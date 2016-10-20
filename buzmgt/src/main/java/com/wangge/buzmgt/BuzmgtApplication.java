package com.wangge.buzmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import com.wangge.buzmgt.monthtask.entity.AppServer;

@EnableConfigurationProperties({AppServer.class}) 
@SpringBootApplication
public class BuzmgtApplication extends SpringBootServletInitializer {
	
  public static void main(String[] args) {
    SpringApplication.run(BuzmgtApplication.class, args);
  }
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	  application.context().getEnvironment().setActiveProfiles(profiles);
		return application.sources(BuzmgtApplication.class);
	}
	
}
