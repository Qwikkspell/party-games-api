package com.qwikkspell.partygamesapi.security;

import com.qwikkspell.partygamesapi.security.ApiKeyAuthenticationFilter;
import com.qwikkspell.partygamesapi.service.ApiKeyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final ApiKeyService apiKeyService;

    public SecurityConfig(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        // Create the custom API key filter
        ApiKeyAuthenticationFilter apiKeyFilter = new ApiKeyAuthenticationFilter(apiKeyService, authenticationManager);

        // Configure the filter chain
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
