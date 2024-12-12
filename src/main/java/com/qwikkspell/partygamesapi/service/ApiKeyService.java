package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.ApiKey;
import com.qwikkspell.partygamesapi.repository.ApiKeyRepository;
import com.qwikkspell.partygamesapi.util.ApiKeyHasher;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String generateApiKey(String role) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        String plainApiKey = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        String hashedApiKey = ApiKeyHasher.hashApiKey(plainApiKey);
        ApiKey apiKeyEntity = new ApiKey(hashedApiKey, role);
        apiKeyRepository.save(apiKeyEntity);
        return plainApiKey;
    }

    public Optional<ApiKey> findActiveAdminKey() {
       return apiKeyRepository.findFirstByRoleAndIsActive("admin", true);
    }

    public Boolean validateApiKey(String apiKey, String method) {
        System.out.println("Validating api key: " + apiKey + " method: " + method);
        String hashedApiKey = ApiKeyHasher.hashApiKey(apiKey);
        System.out.println("Hashed api key: " + hashedApiKey);
        return apiKeyRepository.findByApiKey(hashedApiKey)
                .map(key -> {
                    System.out.println("API Key found in database: " + key);
                    if (method.equalsIgnoreCase("GET")) {
                        return true;
                    } else if (method.equalsIgnoreCase("POST") ||
                                method.equalsIgnoreCase("DELETE") ||
                                method.equalsIgnoreCase("PUT")) {
                        return "admin".equals(key.getRole());
                    }
                    return false;
                })
                .orElseGet(() -> {
                    System.out.println("API Key not found in database");
                    return false;
                });

    }

}
