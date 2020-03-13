package com.sifast.springular.framework.business.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.sifast.springular.framework.business.logic.entities", "com.sifast.springular.framework.business.logic.web.service.impl", "com.sifast.springular.framework.business.logic.service",
    "com.sifast.springular.framework.business.logic.service.impl", "com.sifast.springular.framework.business.logic.dao", "com.sifast.springular.framework.business.logic.web.service.api",
    "com.sifast.springular.framework.business.logic.web.config", "com.sifast.springular.framework.business.logic.web.mapper"})
@EnableJpaRepositories({"com.sifast.springular.framework.business.logic.dao", "com.sifast.springular.framework.business.logic.entities"})
@EnableConfigurationProperties
@EntityScan("com.sifast.springular.framework.business.logic.entities")
@EnableSwagger2

public class SpringularFrameworkBusinessLogicApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(SpringularFrameworkBusinessLogicApplication.class, args);
	}
	
	
	
		
	

}






