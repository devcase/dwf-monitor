package br.com.devcase.webmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@EnableAutoConfiguration
@Configuration
@ComponentScan
@Profile("backoffice")
public class WebMonitorBackofficeApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebMonitorBackofficeApplication.class);
		app.setAdditionalProfiles("backoffice");
		app.run(args);
		
	}
	

}
