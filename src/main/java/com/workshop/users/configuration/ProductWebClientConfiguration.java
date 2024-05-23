package com.workshop.users.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ProductWebClientConfiguration {
    @Bean
    public RestClient.Builder restClientBuilder(){
        return RestClient.builder().baseUrl("http://localhost:8081");
    }
}
