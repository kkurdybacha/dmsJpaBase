package pl.com.bottega.dms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.api.DocumentDto;
import pl.com.bottega.dms.api.DocumentReader;
import pl.com.bottega.dms.api.DocumentSearchCriteria;
import pl.com.bottega.dms.model.Document;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class JPADocumentReader implements DocumentReader {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DocumentDto> searchDocumentDtos(DocumentSearchCriteria criteria) {
        return null;
    }

    @Override
    public DocumentDto get(Long id) {
        return null;
    }
}
