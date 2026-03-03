package org.estar.apimocking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mock_configurations")
@Data //use Lombok for Getter/Setter
public class MockConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mock_id")
    private String mockId;

    @Column(name = "owner_user_name", nullable = false)
    private String ownerUsername;

    @Column(nullable = false)
    private String method; //eg GET, POST, PUT, DELETE

    @Column(nullable = false)
    private String path; //eg. api/products/123

    @Column(name = "status_code", nullable = false)
    private Integer statusCode;

    @Column(columnDefinition = "TEXT")
    private String responseBody; // The raw JSON payload to return

    @Column(name = "latency_ms")
    private int latencyMs; // Chaos: Delay in milliseconds

    @Column(name = "error_rate")
    private int errorRatePercentage; // Chaos: % chance to fail

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", nullable = false)
    @JsonIgnore
    private MockCollection collection;

    @Column(name = "collection_id", insertable = false, updatable = false)
    private String collectionId;

    public String getCollectionId() {
        if (this.collection != null) {
            return this.collection.getId();
        }
        return this.collectionId;
    }
}
