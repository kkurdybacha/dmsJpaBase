package pl.com.bottega.dms.infrastructure;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.NoSuchDocumentException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Document document) {
        entityManager.persist(document);
    }

    @Override
    public Document load(String number) {
        Session session = entityManager.unwrap(Session.class);
        Document document = session.byNaturalId(Document.class).
                using("number", number).
                load();
        if(document == null)
            throw new NoSuchDocumentException();
        return document;
    }

    @Override
    public void remove(String number) {
       Document document = load(number);
       entityManager.remove(document);
    }
}
