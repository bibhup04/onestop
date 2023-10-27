package com.service.onestopgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableDiscoveryClient
// @EnableWebMvc
public class OnestopGatewayApplication {
  
	
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(OnestopGatewayApplication.class, args);
	}

}
