package com.qwikkspell.partygamesapi.util;


import com.qwikkspell.partygamesapi.entity.ApiKey;
import com.qwikkspell.partygamesapi.service.ApiKeyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminApiKeyInitializer implements CommandLineRunner {
    private final ApiKeyService apiKeyService;

    public AdminApiKeyInitializer(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<ApiKey> existingAdminKey = apiKeyService.findActiveAdminKey();
        if (existingAdminKey.isEmpty()) {
            String newAdminKey = apiKeyService.generateApiKey("admin");
            System.out.println("generated new admin api key: " + newAdminKey);


        } else {
            System.out.println("admin key already exists");
        }
    }
}
