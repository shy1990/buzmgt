package com.wangge.buzmgt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.wangge.buzmgt.monthTask.DataEventHandler;
import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.entity.MonthTask;

@Configuration
public class RepositiryConfig extends RepositoryRestConfigurerAdapter {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(MonthOdersData.class,MonthTask.class);
		config.findRepositoryMappingForPath("**/monthTask/*");
	}
	
	@Bean
	DataEventHandler taskdataEventHandler() {
		return new DataEventHandler();
	}
}
