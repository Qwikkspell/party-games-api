package com.qwikkspell.partygamesapi.security;

import com.qwikkspell.partygamesapi.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends GenericFilterBean {
    private final ApiKeyService apiKeyService;

    public ApiKeyAuthenticationFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String apiKey = httpRequest.getHeader("API-Key");
        String method = httpRequest.getMethod();

        if (apiKey == null || !apiKeyService.validateApiKey(apiKey, method)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        ApiKeyAuthenticationToken authToken = new ApiKeyAuthenticationToken(apiKey, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        chain.doFilter((ServletRequest) request, (ServletResponse) response);
    }
}
