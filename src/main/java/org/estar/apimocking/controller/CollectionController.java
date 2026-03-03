package org.estar.apimocking.controller;

import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import org.estar.apimocking.models.MockCollection;
import org.estar.apimocking.models.MockConfiguration;
import org.estar.apimocking.repository.MockCollectionRepository;
import org.estar.apimocking.service.MockCollectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{username}/api/_admin/collections")
public class CollectionController {
    private MockCollectionService collectionService;

    private record NewCollectionDTO(String name){};

    CollectionController(MockCollectionService mockCollectionService)
    {
        this.collectionService = mockCollectionService;
    }

    @GetMapping("")
    @PreAuthorize("@mockSecurity.isDomainOwner(authentication, #username)")
    List<MockCollection> getCollections(@PathVariable String username)
    {
        return collectionService.getCollections(username);
    }

    @PreAuthorize("@mockSecurity.isDomainOwner(authentication, #username)")
    @PostMapping("")
    MockCollection addNewMockCollection(@PathVariable String username, @RequestBody NewCollectionDTO request)
    {
        return collectionService.createCollection(username, request.name());
    }

    @PutMapping("/{id}")
    @PreAuthorize("@mockSecurity.isCollectionOwner(authentication, #id)")
    MockCollection updateCollection(@PathVariable String username, @PathVariable String id, @RequestBody @Valid MockCollection request)
    {
        return collectionService.updateCollection(username, id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@mockSecurity.isCollectionOwner(authentication, #id)")
    void deleteCollection(@PathVariable String username, @PathVariable String id, ServletRequest servletRequest)
    {
        collectionService.deleteCollection(username, id);
    }
}
