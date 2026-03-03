package org.estar.apimocking.service.impl;

import org.estar.apimocking.exceptions.ResourceAlreadyExistException;
import org.estar.apimocking.exceptions.ResourceNotFoundException;
import org.estar.apimocking.models.MockCollection;
import org.estar.apimocking.repository.MockCollectionRepository;
import org.estar.apimocking.service.MockCollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockCollectionServiceImpl implements MockCollectionService {
    private final MockCollectionRepository  collectionRepo;

    MockCollectionServiceImpl(MockCollectionRepository mockCollectionRepository)
    {
        this.collectionRepo = mockCollectionRepository;
    }

    @Override
    public List<MockCollection> getCollections(String username) {
        return collectionRepo.findAllByOwnerUsername(username);
    }

    @Override
    public MockCollection updateCollection(String username, String collectionId, MockCollection collection) {
        if(!collectionRepo.existsByIdAndOwnerUsername(collectionId, username))
        {
            throw new ResourceNotFoundException("Destination collection not found or you dont have permission.");
        }
        collection.setOwnerUsername(username);
        collection.setId(collectionId);
        return collectionRepo.save(collection);
    }

    @Override
    public MockCollection createCollection(String username, String collectionName) {
        if (collectionRepo.existsByOwnerUsernameAndCollectionName(username, collectionName))
        {
            throw new ResourceAlreadyExistException("Collection name exist. Choose another one.");
        }
        MockCollection newCollection = new MockCollection();
        newCollection.setCollectionName(collectionName);
        newCollection.setOwnerUsername(username);
        return collectionRepo.save(newCollection);
    }

    @Override
    public void deleteCollection(String username, String collectionId) {
        if(!collectionRepo.existsByIdAndOwnerUsername(collectionId, username))
        {
            throw new ResourceNotFoundException("Collection not exist or you don't have permission.");
        }
        collectionRepo.deleteById(collectionId);
    }
}
