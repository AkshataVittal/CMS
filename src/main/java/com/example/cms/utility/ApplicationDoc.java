package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDoc {

	Contact contact() {
		return new Contact().name("Ranco").url("xyz.in").email("rancho@gmail.com");
	}

	//which holds information about the application and the owner and the licence information
	//OpenAPIDefinition --> it is class which is root bean
	//Info --> which is also bean 
	Info info() {	
		return new Info().title("Content Management Sysytem")
				.description("Restful API with basic CRUD operatiopns")
				.version("v1").contact(contact()).license(null);
	}
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}
}


