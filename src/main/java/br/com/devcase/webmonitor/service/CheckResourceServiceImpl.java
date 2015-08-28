package br.com.devcase.webmonitor.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.devcase.webmonitor.persistence.dao.MonitoredResourceDAO;
import br.com.devcase.webmonitor.persistence.domain.MonitoredResource;
import dwf.slack.SlackService;

@Service
public class CheckResourceServiceImpl implements CheckResourceService {
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private MonitoredResourceDAO monitoredResourceDAO;
	@Autowired(required=false)
	private SlackService slackService;
	@Value("${web-monitor.slackchannel:#general}")
	private String slackChannel;
	public String getSlackChannel() {
		return slackChannel;
	}
	public void setSlackChannel(String slackChannel) {
		this.slackChannel = slackChannel;
	}


	public void checkPending() throws IOException {
		List<MonitoredResource> pending = monitoredResourceDAO.findByFilter("pending", Boolean.TRUE);

		CloseableHttpClient hc = HttpClientBuilder.create().build();
		try {
			for (MonitoredResource monitoredResource : pending) {
				log.info("Checking health for: " + monitoredResource);
				monitoredResourceDAO.evict(monitoredResource);
				monitoredResource.setLastHealthCheck(new Date());
				
				HttpGet g = new HttpGet(monitoredResource.getHealthUrl());
				
				if(monitoredResource.getHealthCheckTimeout() != null) {
					RequestConfig reqConfig = RequestConfig.copy(g.getConfig()).setConnectTimeout(monitoredResource.getHealthCheckTimeout().intValue()).setSocketTimeout(monitoredResource.getHealthCheckTimeout().intValue()).build();
					g.setConfig(reqConfig);
				}
				CloseableHttpResponse response;
				try {
					response = hc.execute(g);
				} catch (Exception ex) {
					log.debug("Exception connecting to health check", ex);
					response = null;
				}
				
				final int expectedStatusCode = monitoredResource.getExpectedHttpCode() != null ? monitoredResource.getExpectedHttpCode().intValue() : HttpStatus.OK.value();
				final boolean statusCodeCheck = response != null && response.getStatusLine() != null && response.getStatusLine().getStatusCode() == expectedStatusCode;
				boolean responseContainsCheck = true;
				if(StringUtils.isNotBlank(monitoredResource.getExpectedText())) {
					if(response == null) {
						responseContainsCheck = false;
					} else {
						responseContainsCheck = response.toString().contains(monitoredResource.getExpectedText());
					}
				}
				
				monitoredResource.setLastHttpCode(response != null && response.getStatusLine() != null ? response.getStatusLine().getStatusCode() : null);
				
				Boolean previousCheckResult = monitoredResource.getHealthCheckResult();
				if(!statusCodeCheck || !responseContainsCheck) {
					//ERROR
					log.info("Response got from service: " + response);
					monitoredResource.setHealthCheckResult(Boolean.FALSE);
					monitoredResource.setNextHealthCheck(
							DateUtils.addMinutes(new Date(), monitoredResource.getHealthCheckPeriodOnError() != null
									? monitoredResource.getHealthCheckPeriodOnError() : 5));
					
					if(previousCheckResult != Boolean.FALSE) {
						monitoredResource.setLastError(new Date());
					}
					
					long errorDuration = 0;
					if(monitoredResource.getLastError() != null) {
						errorDuration = System.currentTimeMillis() - monitoredResource.getLastError().getTime();
						monitoredResource.setLastErrorDuration(errorDuration);
					}
					
					//First alert?
					long downtimeAlertMillis = (monitoredResource.getDowntimeAlert() != null ? monitoredResource.getDowntimeAlert().intValue() : 10) * 1000;
					long alertPeriodMillis = (monitoredResource.getNewAlertPeriod() != null ? monitoredResource.getNewAlertPeriod().intValue() : 10) * 1000;
					long lastAlertMillis = monitoredResource.getLastAlertTime() != null ? (System.currentTimeMillis() - monitoredResource.getLastAlertTime().getTime()) : Integer.MAX_VALUE;  
					boolean alert = (errorDuration > downtimeAlertMillis) && (lastAlertMillis > alertPeriodMillis); 
					if(alert) {
						monitoredResource.setLastAlertTime(new Date());
						String mention = monitoredResource.getAlertMentions();
						if(mention == null) mention = "@channel";
						String message= mention + ": Down for " + (errorDuration/(1000*60 )) + " minutes: " + monitoredResource;
						//notification
						if(slackService != null) {
							slackService.postMessage(slackChannel, message, "icon_emoji", ":boom:");
						} else {
							log.warn(message);
						}
					}
				} else {
					
					//OK!
					monitoredResource.setHealthCheckResult(Boolean.TRUE);
					monitoredResource.setNextHealthCheck(
							DateUtils.addMinutes(new Date(), monitoredResource.getHealthCheckPeriod() != null
									? monitoredResource.getHealthCheckPeriod() : 60));
					
					if(previousCheckResult == Boolean.FALSE) {
						long errorDuration = 0;
						if(monitoredResource.getLastError() != null) {
							errorDuration = System.currentTimeMillis() - monitoredResource.getLastError().getTime();
							monitoredResource.setLastErrorDuration(errorDuration);
						}
						
						//notification
						String mention = monitoredResource.getAlertMentions();
						if(mention == null) mention = "@channel";

						String message =  mention + ": Back to normal after " + (errorDuration/(1000*60)) + " minutes: " + monitoredResource;
						if(slackService != null) {
							slackService.postMessage(slackChannel, message, "icon_emoji", ":metal:");
						} else {
							log.info(message);
						}
					}
				}
				
				monitoredResourceDAO.updateByAnnotation(monitoredResource, MonitoredResource.HealthCheckUpdate.class);
				response.close();
			}
		} finally {
			hc.close();
		}
	}
}
