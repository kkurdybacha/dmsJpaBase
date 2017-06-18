package pl.com.bottega.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.api.DocumentReader;
import pl.com.bottega.dms.api.DocumentSearchCriteria;
import pl.com.bottega.dms.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentReader documentReader;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Document document = new Document();
        document.setTitle("doc");
        document.setContent("hello world!!!");
        document.setCreatedAt(LocalDateTime.now());
        document.setNumber("1");
        document.setStatus(DocumentStatus.DRAFT);
        User user = new User();
        user.setLogin("john");
        user.setPassword("111111");
        Employee author = new Employee();
        user.setEmployee(author);
        document.setAuthor(author);
        transactionTemplate.execute((callback) -> {
            entityManager.persist(author);
            entityManager.persist(user);
            entityManager.persist(document);
            return null;
        });


        System.out.println("Author id: " + author.getId());
        System.out.println("User id: " + user.getId());
        System.out.println("Document id: " + document.getId());

        transactionTemplate.execute((callback) -> {
            document.setVerifiedAt(LocalDateTime.now());
            document.setVerifier(author);
            document.setPublishedAt(LocalDateTime.now());
            document.setPublisher(author);
            document.getReaders().add(author);
            entityManager.merge(document);
            return null;
        });
        User user2 = new User();
        Employee employee = new Employee();
        user2.setEmployee(employee);
        employee.setUser(user2);
        transactionTemplate.execute((c) -> {
            entityManager.persist(user2);
            return null;
        });

        transactionTemplate.execute((c) -> {
            User u = entityManager.merge(user2);
            entityManager.remove(u);
            return null;
        });

        transactionTemplate.execute((c) -> {
            User u = new User();
            Employee e = new Employee();
            u.setEmployee(e);
            Document doc = new Document();
            doc.setAuthor(e);
            e.getCreatedDocuments().add(doc);
            entityManager.persist(u);
            return null;
        });

        transactionTemplate.execute((c) -> {
            Employee e = entityManager.find(Employee.class, 3L);
            for (Document doc : e.getCreatedDocuments())
                doc.setAuthor(null);
            entityManager.remove(e);
            return null;
        });

        Document dd = entityManager.find(Document.class, 1L);
        //dd.getAuthor().getId();

        Document ddd = new Document();
        transactionTemplate.execute((c) -> {
            documentRepository.save(ddd);
            return null;
        });

        transactionTemplate.execute((c) -> {
            Document dddd = documentRepository.findByNumber("1").get();
            System.out.println(dddd.getId());
            return null;
        });


        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setAuthorId(1L);
        criteria.setContent("hell");
        criteria.setTitle("do");
        criteria.setStatus(DocumentStatus.DRAFT);
        List<Document> docs = documentReader.searchDocuments(criteria);
        System.out.println(docs.size());
    }

}
