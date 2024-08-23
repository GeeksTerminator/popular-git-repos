package com.redcare_pharmacy.popular_git_repos.config;

import com.redcare_pharmacy.popular_git_repos.client.GithubApiErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new GithubApiErrorHandler());
        return restTemplate;
    }
}
