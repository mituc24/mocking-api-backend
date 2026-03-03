package org.estar.apimocking.service;

import org.estar.apimocking.models.MockConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MockConfigurationService {
    public MockConfiguration saveConfiguration(String username,MockConfiguration mockConfiguration, String collectionId);
    //public List<MockConfiguration> getAllConfigurations(String username);
    public MockConfiguration updateConfiguration(String username, String id, MockConfiguration mockConfiguration);
    public void deleteConfiguration(String username, String id);
    public MockConfiguration findByIdAndUsername(String id, String username);
    List<MockConfiguration> searchConfigurations(String username, String keyword, String method, String collectionId);
}
