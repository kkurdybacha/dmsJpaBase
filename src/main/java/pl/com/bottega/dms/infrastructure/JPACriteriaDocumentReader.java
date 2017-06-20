package pl.com.bottega.dms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.api.DocumentDto;
import pl.com.bottega.dms.api.DocumentReader;
import pl.com.bottega.dms.api.DocumentSearchCriteria;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class JPACriteriaDocumentReader implements DocumentReader {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Document> searchDocuments(DocumentSearchCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> criteriaQuery = builder.createQuery(Document.class);

        Root<Document> root = criteriaQuery.from(Document.class);
        Set<Predicate> conditions = new HashSet<>();
        if (criteria.getStatus() != null)
            conditions.add(builder.equal(root.get("status"), criteria.getStatus()));
        if (criteria.getContent() != null) {
            conditions.add(builder.like(root.get("content"), "%" + criteria.getContent() + "%"));
        }
        if (criteria.getTitle() != null) {
            conditions.add(builder.like(root.get("title"), "%" + criteria.getTitle() + "%"));
        }
        if (criteria.getAuthorId() != null) {
            Join<Document, Employee> author = root.join("author");
            conditions.add(builder.equal(author.get("id"), criteria.getAuthorId()));
        }
        root.fetch("readers", JoinType.LEFT);
        criteriaQuery.distinct(true).where(conditions.toArray(new Predicate[] {}));

        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<DocumentDto> searchDocumentDtos(DocumentSearchCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> criteriaQuery = builder.createQuery(DocumentDto.class);

        Root<Document> root = criteriaQuery.from(Document.class);
        Set<Predicate> conditions = new HashSet<>();
        if (criteria.getStatus() != null)
            conditions.add(builder.equal(root.get("status"), criteria.getStatus()));
        if (criteria.getContent() != null) {
            conditions.add(builder.like(root.get("content"), "%" + criteria.getContent() + "%"));
        }
        if (criteria.getTitle() != null) {
            conditions.add(builder.like(root.get("title"), "%" + criteria.getTitle() + "%"));
        }
        if (criteria.getAuthorId() != null) {
            Join<Document, Employee> author = root.join("author");
            conditions.add(builder.equal(author.get("id"), criteria.getAuthorId()));
        }
        criteriaQuery.where(conditions.toArray(new Predicate[] {}));
        criteriaQuery.select(builder.construct(DocumentDto.class,
                root.get("number"), root.get("status"), root.get("content"), root.get("title")
        ));

        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
