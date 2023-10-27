package com.service.onestopgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    
    /** 
     * @return RestTemplate
     */
    @Bean
    public RestTemplate template(){
       return new RestTemplate();
    }
}
