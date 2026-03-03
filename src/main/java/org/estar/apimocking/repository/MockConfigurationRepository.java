package org.estar.apimocking.repository;

import jakarta.validation.constraints.NotBlank;
import org.estar.apimocking.models.MockConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockConfigurationRepository extends JpaRepository<MockConfiguration,String> {
    MockConfiguration findByMethodAndPathAndOwnerUsername(String method, String path, String ownerUsername);
    boolean existsByMockIdAndOwnerUsername(String id, String username);
    List<MockConfiguration> findAllByOwnerUsername(String ownerUsername);
    boolean existsByOwnerUsernameAndMethodAndPath(String ownerUsername, @NotBlank(message = "Method must not be blank") String method, @NotBlank(message = "Path must not be blank") String path);
    MockConfiguration findByMockIdAndOwnerUsername(String mockId, String ownerUsername);
    MockConfiguration findByOwnerUsernameAndMethodAndPath(String ownerUsername, @NotBlank(message = "Method must not be blank") String method, @NotBlank(message = "Path must not be blank") String path);
    boolean existsByMethodAndPathAndOwnerUsername(@NotBlank(message = "Method must not be blank") String method, @NotBlank(message = "Path must not be blank") String path, String username);
    @Query("SELECT m FROM MockConfiguration m WHERE m.ownerUsername = :username " +
            "AND (:keyword IS NULL OR LOWER(m.path) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:method IS NULL OR m.method = :method) " +
            "AND (:collectionId IS NULL OR m.collection.id = :collectionId)")
    List<MockConfiguration> searchMocks(
            @Param("username") String username,
            @Param("keyword") String keyword,
            @Param("method") String method,
            @Param("collectionId") String collectionId
    );
}
