package pl.com.bottega.dms.infrastructure;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Document document) {
        entityManager.merge(document);
    }

    @Override
    public Optional<Document> findByNumber(String number) {
        Session session = entityManager.unwrap(Session.class);
        return Optional.ofNullable(
                session.byNaturalId(Document.class).
                        using("number", number).
                        load()
        );
    }
}
