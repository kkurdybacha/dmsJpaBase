package pl.com.bottega.dms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNotFoundException;
import pl.com.bottega.dms.model.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Document get(Long id) throws DocumentNotFoundException {
        Document document = entityManager.find(Document.class, id);
        if(document == null)
            throw new DocumentNotFoundException();
        return document;
    }

    @Override
    public void save(Document document) {
        entityManager.persist(document);
    }

    @Override
    public void remove(Long id) throws DocumentNotFoundException {
        Document document = get(id);
        entityManager.remove(document);
    }
}
