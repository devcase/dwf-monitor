package br.com.devcase.webmonitor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.com.devcase.webmonitor.service.CheckResourceService;
import dwf.slack.SlackService;

@Component
@Profile(value="worker")
public class WebMonitorWorker {
	
	@Autowired
	private CheckResourceService checkResourceService;
	@Autowired
	private SlackService slackService;
	@Autowired
	private Environment environment;
	
	@PostConstruct
	public void startThread() {

        final Thread awaitSigtermThread = new Thread("Check resources thread") {
			@Override
			public void run() {
	            try {
	                while(true) { 
	                	try {
	                        System.out.println("Resource checking starting" );
	                        checkResourceService.checkPending();
	                        System.out.println("Resource checking finished");
	                	} catch (Exception ex) {
	                		ex.printStackTrace();
	                	}
	                	Thread.sleep( 60000 ); 
	                }
                } catch( InterruptedException ex ) {
                }
			}
        };
        awaitSigtermThread.start();
        

		String slackChannel = environment.getProperty("web-monitor.slackchannel");
		if(slackChannel == null) slackChannel = "#general";
		slackService.postMessage(slackChannel, "WebMonitor is up!");
	}
}
