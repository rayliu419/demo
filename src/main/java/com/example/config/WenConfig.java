package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WenConfig {

    @Bean
    public RestTemplate newRestTemplate() {
        return new RestTemplate();
    }

}
