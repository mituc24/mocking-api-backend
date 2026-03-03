package org.estar.apimocking.service;

import org.estar.apimocking.models.MockCollection;

import java.util.List;

public interface MockCollectionService {
    public List<MockCollection> getCollections(String username);
    public MockCollection updateCollection(String username, String collectionId, MockCollection collection);
    public MockCollection createCollection(String username, String collectionName);
    public void deleteCollection(String username, String collectionId);
}
