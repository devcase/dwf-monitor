package br.com.devcase.webmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

import br.com.devcase.webmonitor.service.CheckResourceService;
import dwf.slack.SlackServiceImpl;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@Profile("worker")
public class WebMonitorWorkerApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebMonitorWorkerApplication.class);
		app.setWebEnvironment(false);
		app.setAdditionalProfiles("worker");

		final ConfigurableApplicationContext ctx = app.run(args);
		
		
        // register a shutdown hook with the JVM
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down " + WebMonitorWorkerApplication.class.getName());
                ctx.close();
            }
        });
        Assert.notNull(ctx.getBean(CheckResourceService.class), "checkResourceService não encontrado!!!");
        
        final Thread awaitSigtermThread = new Thread("Check resources thread") {
			@Override
			public void run() {
	            try {
	                while(true) { 
	                	try {
	                		CheckResourceService service = ctx.getBean(CheckResourceService.class);
	                        System.out.println("Resource checking starting" );
	                		service.checkPending();
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
        
		SlackServiceImpl s = ctx.getBean(SlackServiceImpl.class);
		String slackChannel = ctx.getEnvironment().getProperty("web-monitor.slackchannel");
		if(slackChannel == null) slackChannel = "#general";
		s.postMessage(slackChannel, "WebMonitor is up!");
        
        System.out.println(WebMonitorWorkerApplication.class.getName() + " started" );
	}

}
