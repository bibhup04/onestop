package com.service.onestopgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OnestopGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnestopGatewayApplication.class, args);
	}

}
