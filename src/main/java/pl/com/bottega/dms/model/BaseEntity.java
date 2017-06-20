package pl.com.bottega.dms.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(EntityHistoryListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    void created() {
        this.createdAt = LocalDateTime.now();
    }

    void updated() {
        this.updatedAt = LocalDateTime.now();
    }
}
