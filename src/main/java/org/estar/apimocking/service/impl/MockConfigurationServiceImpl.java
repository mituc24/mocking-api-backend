package org.estar.apimocking.service.impl;

import org.estar.apimocking.exceptions.ResourceAlreadyExistException;
import org.estar.apimocking.exceptions.ResourceNotFoundException;
import org.estar.apimocking.importer.ApiImportStrategy;
import org.estar.apimocking.models.MockCollection;
import org.estar.apimocking.models.MockConfiguration;
import org.estar.apimocking.repository.MockCollectionRepository;
import org.estar.apimocking.repository.MockConfigurationRepository;
import org.estar.apimocking.service.MockConfigurationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MockConfigurationServiceImpl implements MockConfigurationService {
    private final MockConfigurationRepository mockRepo;
    private final List<ApiImportStrategy> importStrategies;
    private final MockCollectionRepository collectionRepo;

    MockConfigurationServiceImpl(MockConfigurationRepository mockRepo, List<ApiImportStrategy> importStrategies, MockCollectionRepository mockCollectionRepository){
        this.mockRepo = mockRepo;
        this.importStrategies = importStrategies;
        this.collectionRepo = mockCollectionRepository;
    }

    @Override
    public MockConfiguration saveConfiguration(String username, MockConfiguration mockConfiguration, String collectionId) {
        if(mockRepo.existsByOwnerUsernameAndMethodAndPath(username,mockConfiguration.getMethod(),mockConfiguration.getPath()))
        {
            throw new ResourceAlreadyExistException("Configs with method "
                    +mockConfiguration.getMethod()+" and path '"+mockConfiguration.getPath()
                    +"' already exist");
        }
        Optional<MockCollection> collection = collectionRepo.findByIdAndOwnerUsername(collectionId, username);
        if(collection.isEmpty())
        {
            throw new ResourceNotFoundException("Collection does not exist or you don't have permission.");
        }
        mockConfiguration.setOwnerUsername(username);
        mockConfiguration.setCollection(collection.get());
        return mockRepo.save(mockConfiguration);
    }

    @Override
    public MockConfiguration updateConfiguration(String username, String id, MockConfiguration mockConfiguration) {
        if(!mockRepo.existsByMockIdAndOwnerUsername(id, username))
        {
            throw new ResourceNotFoundException("The config is not exist or you don't have permission");
        }
        mockConfiguration.setMockId(id);
        mockConfiguration.setOwnerUsername(username);
        return mockRepo.save(mockConfiguration);
    }

    @Override
    public void deleteConfiguration(String username, String id) {
        if(!mockRepo.existsByMockIdAndOwnerUsername(id, username))
        {
            throw new ResourceNotFoundException("The resource not found or you don't have permission");
        }
        mockRepo.deleteById(id);
    }

    @Override
    public MockConfiguration findByIdAndUsername(String id, String username) {
        if(!mockRepo.existsByMockIdAndOwnerUsername(id, username))
        {
            throw new ResourceNotFoundException("The resource not found or you don't have permission");
        }
        return mockRepo.findByMockIdAndOwnerUsername(id, username);
    }

    @Override
    public List<MockConfiguration> searchConfigurations(String username, String keyword, String method, String collectionId) {
        String safeKeyword = (keyword != null && !keyword.isBlank()) ? keyword : null;
        String safeMethod = (method != null && !method.isBlank()) ? method : null;
        String safeCollectionId = (collectionId != null && !collectionId.isBlank()) ? collectionId : null;

        return mockRepo.searchMocks(username, safeKeyword, safeMethod, safeCollectionId);
    }
}
