package com.horizon.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI horizonAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Horizon API")
                        .version("1.0.0")
                        .description("Documentation for Horizon API (Characters, Machines, Areas, Regions, Cauldrons).")
                        .termsOfService("")
                        .contact(new Contact()
                                .name("Luis Angel Rocha Ronquillo")
                                .url("https://luisrocharo.com")
                                .email("luis.rocha021231@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                        );
    }
}
