package com.qwikkspell.partygamesapi.security;

import com.qwikkspell.partygamesapi.service.ApiKeyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class ApiKeyAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final ApiKeyService apiKeyService;

    public ApiKeyAuthenticationFilter(ApiKeyService apiKeyService, AuthenticationManager authenticationManager ) {
        this.apiKeyService = apiKeyService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {
        String apiKey = request.getHeader("API-Key");
        if (apiKey != null && apiKeyService.validateApiKey(apiKey, request.getMethod())) {
            return apiKey;
        }
        return null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {
        return "";
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }
}
