package com.sifast.convertToJava.tn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.sifast.convertToJava.tn.entities", "com.sifast.convertToJava.tn.web.service.impl", "com.sifast.convertToJava.tn.service",
    "com.sifast.convertToJava.tn.service.impl", "com.sifast.convertToJava.tn.dao", "com.sifast.convertToJava.tn.web.service.api",
    "com.sifast.convertToJava.tn.web.config", "com.sifast.convertToJava.tn.web.mapper"})
@EnableJpaRepositories({"com.sifast.convertToJava.tn.dao", "com.sifast.convertToJava.tn.entities"})
@EnableConfigurationProperties
@EntityScan("com.sifast.convertToJava.tn.entities")

public class XmlToJavaApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(XmlToJavaApplication.class, args);
	}
	
	
	
		
	

}






