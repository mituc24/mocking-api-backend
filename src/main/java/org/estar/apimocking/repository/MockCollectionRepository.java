package org.estar.apimocking.repository;

import org.estar.apimocking.models.MockCollection;
import org.estar.apimocking.models.MockConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MockCollectionRepository extends JpaRepository<MockCollection,String> {
    List<MockCollection> findAllByOwnerUsername(String username);

    boolean existsByIdAndOwnerUsername(String collectionId, String username);

    boolean existsByOwnerUsernameAndCollectionName(String username, String collectionName);

    Optional<MockCollection> findByIdAndOwnerUsername(String collectionId, String ownerUsername);
}
