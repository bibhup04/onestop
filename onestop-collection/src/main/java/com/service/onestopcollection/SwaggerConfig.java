package com.service.onestopcollection;



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
                .info(new Info().title("ONESTOP-COLLECTION ")
                .description("This microservice runs collection cycle of 14 days and send collection details to Billing Microservice.")
                .contact(new Contact().name("Bibhu").email("bibhusunder.b@prodapt.com")))
                .externalDocs(new ExternalDocumentation()
                .description("Link to this project")
                .url("https://github.com/bibhup04/onestop/tree/main/onestop-collection"));
    }


}