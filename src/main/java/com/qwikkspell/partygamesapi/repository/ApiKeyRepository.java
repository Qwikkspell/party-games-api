package com.qwikkspell.partygamesapi.repository;

import com.qwikkspell.partygamesapi.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findByApiKey(String apiKey);

    Optional<ApiKey> findFirstByRoleAndIsActive(String role, Boolean isActive);
}
