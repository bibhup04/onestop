package com.service.onestopserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class OnestopServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnestopServiceRegistryApplication.class, args);
	}

}
