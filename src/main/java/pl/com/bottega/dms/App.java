package pl.com.bottega.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.Employee;
import pl.com.bottega.dms.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@SpringBootApplication
public class App implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

        Employee publisher = new Employee();
        User publisherUser = new User();
        publisher.setUser(publisherUser);
        publisherUser.setEmployee(publisher);
        transactionTemplate.execute((callback) -> {
            entityManager.persist(publisher);
            entityManager.persist(publisherUser);
            document.setVerifiedAt(LocalDateTime.now());
            document.setVerifier(author);
            document.setPublishedAt(LocalDateTime.now());
            publisher.getPublishedDocuments().add(document);
            document.getReaders().add(author);
            entityManager.merge(document);
            return null;
        });

    }

}
