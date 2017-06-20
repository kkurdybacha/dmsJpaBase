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
import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DocumentReader reader;

    @Autowired
    private DocumentRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Document d1 = new Document();
        d1.setNumber("1");
        d1.setStatus(DocumentStatus.DRAFT);
        Document d2 = new Document();
        d2.setNumber("2");
        d2.setStatus(DocumentStatus.DRAFT);
        Document d3 = new Document();
        d3.setNumber("3");
        d3.setStatus(DocumentStatus.DRAFT);
        Employee e1 = new Employee();
        Employee e2 = new Employee();
        transactionTemplate.execute((c) -> {
            d1.getReaders().add(e1);
            d1.getReaders().add(e2);
            d2.getReaders().add(e1);
            d2.getReaders().add(e2);
            d3.getReaders().add(e1);
            d3.getReaders().add(e2);
            repo.save(d1);
            repo.save(d2);
            repo.save(d3);
            return null;
        });
        transactionTemplate.execute((c) -> {
            DocumentSearchCriteria crit = new DocumentSearchCriteria();
            crit.setStatus(DocumentStatus.DRAFT);
            List<Document> docs = reader.searchDocuments(crit);
            System.out.println("================" + docs.size());
            for(Document d : docs)
                d.getReaders().size();
            return null;
        });
    }

}
