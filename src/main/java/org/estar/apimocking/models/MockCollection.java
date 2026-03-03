package org.estar.apimocking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "mock_collections")
public class MockCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "collection_id", nullable = false)
    private String id;

    @Column(nullable = false)
    private String collectionName;

    @Column(nullable = false)
    private String ownerUsername;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MockConfiguration> mocks = new ArrayList<>();
}
