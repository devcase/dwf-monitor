package br.com.devcase.webmonitor.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import br.com.devcase.webmonitor.persistence.domain.MonitoredResource;
import dwf.web.controller.BaseCrudController;

@Controller
@RequestMapping("/monitoredResource/")
@ConditionalOnWebApplication
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class MonitoredResourceController extends BaseCrudController<MonitoredResource, Long> {

	public MonitoredResourceController() {
		super(MonitoredResource.class);
	}

	@RequestMapping("check/{id}")
	public ResponseEntity<Boolean> check(@PathVariable Long id) {
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

	@RequestMapping("pending")
	public ResponseEntity<List<MonitoredResource>> pending() {
		return new ResponseEntity<>(getDAO().findByFilter("pending", Boolean.TRUE), HttpStatus.OK);
	}

	@RequestMapping("check")
	public Callable<ResponseEntity<Boolean>> check() {
		return new Callable<ResponseEntity<Boolean>>() {

			@Override
			public ResponseEntity<Boolean> call() throws Exception {
				List<MonitoredResource> pending = getDAO().findByFilter("pending", Boolean.TRUE);

				CloseableHttpClient hc = HttpClientBuilder.create().build();
				try {
					System.out.println(hc);
					System.out.println(pending);
					for (MonitoredResource monitoredResource : pending) {
						getDAO().evict(monitoredResource);
						monitoredResource.setLastHealthCheck(new Date());
						
						CloseableHttpResponse response = hc.execute(new HttpGet(monitoredResource.getHealthUrl()));
						if(response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
							//ERROR
							monitoredResource.setHealthCheckResult(Boolean.FALSE);
							monitoredResource.setNextHealthCheck(
									DateUtils.addMinutes(new Date(), monitoredResource.getHealthCheckPeriodOnError() != null
											? monitoredResource.getHealthCheckPeriodOnError() : 5));
						} else {
							//OK!
							monitoredResource.setHealthCheckResult(Boolean.TRUE);
							monitoredResource.setNextHealthCheck(
									DateUtils.addMinutes(new Date(), monitoredResource.getHealthCheckPeriod() != null
											? monitoredResource.getHealthCheckPeriod() : 60));

						}
						getDAO().updateByAnnotation(monitoredResource, MonitoredResource.HealthCheckUpdate.class);
						response.close();
					}

					return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
				} finally {
					hc.close();
				}
			}

		};
	}

}
