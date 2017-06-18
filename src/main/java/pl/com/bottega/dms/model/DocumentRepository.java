package pl.com.bottega.dms.model;

import java.util.Optional;

public interface DocumentRepository {

    void save(Document document);

    Optional<Document> findByNumber(String number);

}
