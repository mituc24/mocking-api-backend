package org.estar.apimocking.security;

import org.estar.apimocking.repository.MockCollectionRepository;
import org.estar.apimocking.repository.MockConfigurationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("mockSecurity")
public class MockSecurityService {
    private final MockConfigurationRepository mockConfigurationRepository;
    private final MockCollectionRepository mockCollectionRepository;

    public MockSecurityService(MockConfigurationRepository mockConfigurationRepository,
                               MockCollectionRepository mockCollectionRepository) {
        this.mockConfigurationRepository = mockConfigurationRepository;
        this.mockCollectionRepository = mockCollectionRepository;
    }

    public boolean isConfigOwner(Authentication authentication, String configId)
    {
        String currentUsername = authentication.getName();
        return mockConfigurationRepository.existsByMockIdAndOwnerUsername(configId, currentUsername);
    }

    public boolean isCollectionOwner(Authentication authentication, String collectionId)
    {
        String currentUsername = authentication.getName();
        return mockCollectionRepository.existsByIdAndOwnerUsername(collectionId, currentUsername);
    }

    public boolean isDomainOwner(Authentication authentication, String username)
    {
        String tokenUsername = authentication.getName();
        return username.equals(tokenUsername);
    }
}
