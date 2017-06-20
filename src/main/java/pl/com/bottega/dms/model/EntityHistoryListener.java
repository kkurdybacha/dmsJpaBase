package pl.com.bottega.dms.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityHistoryListener {

    @PrePersist
    public void prePersist(BaseEntity baseEntity) {
        baseEntity.created();
    }

    @PreUpdate
    public void preUpdate(BaseEntity baseEntity) {
        baseEntity.updated();
    }

}
