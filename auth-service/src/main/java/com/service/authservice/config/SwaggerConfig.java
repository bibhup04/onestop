package com.service.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {
    
     
     /** 
      * @return OpenAPI
      */
     @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Auth service ")
                .description("Microservice for authentication")
                .version("v0.0.1")
                .contact(new Contact().name("Bibhu").email("bibhusunder.b@prodapt.com")))
                .externalDocs(new ExternalDocumentation()
                .description("Link to this project")
                .url("https://github.com/bibhup04/onestop/tree/main/auth-service"));
    }


}
