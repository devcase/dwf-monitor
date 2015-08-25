package br.com.devcase.webmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;


@EnableAutoConfiguration
@Configuration
@ComponentScan
public class WebMonitorApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebMonitorApplication.class);
		
		//If the profile disable-web is active, don't enable the web environment
		StandardEnvironment tempEnv = new StandardEnvironment();
		tempEnv.getPropertySources().addFirst(new SimpleCommandLinePropertySource(args));
		if(tempEnv.acceptsProfiles("disable-web")) {
			app.setWebEnvironment(false);
		}
		
		app.run(args);
	}
	

}
