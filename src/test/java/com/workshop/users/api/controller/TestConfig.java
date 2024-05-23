package com.workshop.users.api.controller;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@TestConfiguration
@Profile("test")
public class TestConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder().baseUrl("http://localhost:8081");
    }
}