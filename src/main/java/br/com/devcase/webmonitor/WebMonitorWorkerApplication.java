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
        
        
        System.out.println(WebMonitorWorkerApplication.class.getName() + " started" );
	}

}
