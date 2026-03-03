package org.estar.apimocking.controller;

import jakarta.validation.Valid;
import org.estar.apimocking.models.MockConfiguration;
import org.estar.apimocking.service.MockConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/{username}/api/_admin")
public class WebController {

    private final MockConfigurationService mockService;

    public WebController(MockConfigurationService mockConfigurationService)
    {
        this.mockService = mockConfigurationService;
    }

    @PreAuthorize("@mockSecurity.isDomainOwner(authentication, #username)")
    @PostMapping("/configs")
    @ResponseStatus(HttpStatus.CREATED)
    public MockConfiguration addMockConfiguration(@RequestBody @Valid MockConfiguration s,
                                                  @PathVariable String username,
                                                  @RequestParam String collectionId)
    {
        return mockService.saveConfiguration(username, s, collectionId);
    }

    @PreAuthorize("@mockSecurity.isConfigOwner(authentication,#id)")
    @PutMapping("/configs/{configs_id}")
    public MockConfiguration updateMockConfiguration(@PathVariable(name = "configs_id") String id, @RequestBody MockConfiguration s, @PathVariable String username)
    {
        return mockService.updateConfiguration(username, id, s);
    }

    @PreAuthorize("@mockSecurity.isConfigOwner(authentication,#id)")
    @DeleteMapping("/configs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMockConfiguration(@PathVariable String id, @PathVariable String username)
    {
        mockService.deleteConfiguration(username, id);
    }

    @GetMapping("/configs/{configId}")
    @PreAuthorize("@mockSecurity.isConfigOwner(authentication,#configId)")
    public MockConfiguration getSpecificConfig(@PathVariable String configId, @PathVariable String username) {
        return mockService.findByIdAndUsername(configId,username);
    }

    @PreAuthorize("@mockSecurity.isDomainOwner(authentication, #username)")
    @GetMapping("/configs/search")
    public List<MockConfiguration> searchMockConfigurations(
            @PathVariable String username,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String collectionId)
    {
        return mockService.searchConfigurations(username, keyword, method, collectionId);
    }
}
