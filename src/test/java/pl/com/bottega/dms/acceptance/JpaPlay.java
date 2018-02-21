package pl.com.bottega.dms.acceptance;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.Employee;
import pl.com.bottega.dms.model.User;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import static org.assertj.core.api.Java6Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaPlay {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private TransactionTemplate transactionTemplate;

  @Test
  public void testInsert() {
    Document document = new Document();

    transactionTemplate.execute(status -> {
      entityManager.persist(document);
      return null;
    });

    Document document1 = entityManager.find(Document.class, 1L);
    assertThat(document1).isNotNull();
  }

  @Test
  public void testUpdate() {
    Document document = new Document();

    transactionTemplate.execute(status -> {
      entityManager.persist(document);
      return null;
    });

    transactionTemplate.execute(status -> {
      Document document1 = entityManager.find(Document.class, 1L);
      document1.verify();
      return null;
    });

    Document document2 = entityManager.find(Document.class, 1L);
    assertThat(document2.version).isNotEqualTo(document.version);
  }

  @Test
  public void testOptimisticLock() {
    Document document = new Document();

    transactionTemplate.execute(status -> {
      entityManager.persist(document);
      return null;
    });

    transactionTemplate.execute(status -> {
      Document document1 = entityManager.find(Document.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
      document1.verify();
      return null;
    });

    Document dd = entityManager.find(Document.class, 1L);
    System.out.println(dd.version);
  }

  @Test
  public void removingUserShouldNotDeleteEmployee() {
    User user = new User();
    Employee employee = new Employee();
    user.setEmployee(employee);
    employee.setUser(user);

    transactionTemplate.execute(c -> {
      entityManager.persist(user);
      return null;
    });

    transactionTemplate.execute(c -> {
      User userFromDb = entityManager.find(User.class, user.getId());
      entityManager.remove(userFromDb);
      return null;
    });

    assertThat(entityManager.find(User.class, user.getId())).isNull();
    assertThat(entityManager.find(Employee.class, employee.getId())).isNotNull();
  }

  @Test
  public void tracksEntities() {
    User user = new User();
    Employee employee = new Employee();
    user.setEmployee(employee);
    employee.setUser(user);

    transactionTemplate.execute(c -> {
      entityManager.persist(user);
      user.setLogin("wacek");
      return null;
    });

    user.setLogin("stefan");

    assertThat(entityManager.find(User.class, 1L).getLogin()).isEqualTo("wacek");
  }

  @Test
  public void tracksEntitiesAfterFind() {
    User user = new User();
    Employee employee = new Employee();
    user.setEmployee(employee);
    employee.setUser(user);

    transactionTemplate.execute(c -> {
      entityManager.persist(user);
      user.setLogin("wacek");
      return null;
    });

    transactionTemplate.execute(c -> {
      User userFromDb = entityManager.find(User.class, 1L);
      userFromDb.setLogin("stefan");
      return null;
    });

    user.setLogin("stefan");

    assertThat(entityManager.find(User.class, 1L).getLogin()).isEqualTo("stefan");
  }

  @Test
  public void tracksEntitiesAfterMerge() {
    User user = new User();
    Employee employee = new Employee();
    user.setEmployee(employee);
    employee.setUser(user);

    transactionTemplate.execute(c -> {
      entityManager.persist(user);
      user.setLogin("wacek");
      return null;
    });

    user.setLogin("krzysiek");

    transactionTemplate.execute(c -> {
      User user1 = entityManager.merge(user);
      user1.setLogin("wiesiek");
      return null;
    });

    assertThat(entityManager.find(User.class, 1L).getLogin()).isEqualTo("wiesiek");
  }

  @Test(expected = LazyInitializationException.class)
  public void cantLazyLoadOutsidePersistenceContext() {
    Auction auction = new Auction();

    transactionTemplate.execute(c -> {
      entityManager.persist(auction);
      return null;
    });

    Auction auction1 = entityManager.find(Auction.class, 1L);
    auction1.bids.size();
  }

  @Test
  public void testCascade() {
    Auction auction = new Auction();
    auction.bids.add(new Bid());

    transactionTemplate.execute(c -> {
      entityManager.persist(auction);
      auction.bids.add(new Bid());
      return null;
    });

    transactionTemplate.execute(c -> {
      Auction auction1 = entityManager.find(Auction.class, 1L);
      assertThat(auction1.bids.size()).isEqualTo(2);
      return null;
    });
  }

  @Test
  public void testJoin() {
    entityManager.createQuery("SELECT au FROM Auction au JOIN au.address adr WHERE adr.city = :c").
        setParameter("c", "Warsaw").
        getResultList();
  }

  @Test
  public void testEmbeddedQuery() {
    entityManager.createQuery("SELECT au FROM Auction au WHERE au.phoneNumber.countryCode = :c").
        setParameter("c", "+48").
        getResultList();
  }

  @Test
  public void testNP1() {
    int n = 100;
    for(int i = 0; i<100; i++) {
      Auction auction = new Auction();
      auction.bids.add(new Bid(5));
      auction.bids.add(new Bid(10));
      auction.bids.add(new Bid(15));
      transactionTemplate.execute(c -> {
        entityManager.persist(auction);
        return null;
      });
    }

    transactionTemplate.execute(c -> {
      List<Auction> auctions = entityManager.createQuery("SELECT DISTINCT a FROM Auction a " +
          "LEFT JOIN FETCH a.bids b WHERE b.amount > 10", Auction.class).
          getResultList(); // 1

      for(Auction auction : auctions) { //n
        System.out.println(auction.bids.size());
      }

      List auctions1 = entityManager.createQuery("SELECT a, b, 1 FROM Auction a " +
          "LEFT JOIN FETCH a.bids b WHERE b.amount <= 10").
          getResultList(); // 1

      return null;
    });


  }

  @Test
  public void testBatchSize() {
    int n = 100;
    for(int i = 0; i<100; i++) {
      Auction auction = new Auction();
      auction.bids.add(new Bid(5));
      auction.bids.add(new Bid(10));
      auction.bids.add(new Bid(15));
      transactionTemplate.execute(c -> {
        entityManager.persist(auction);
        return null;
      });
    }

    transactionTemplate.execute(c -> {
      List<Auction> auctions = entityManager.createQuery("FROM Auction a ", Auction.class).
          getResultList(); // 1 + n/10

      for(Auction auction : auctions) {
        System.out.println(auction.bids.size());
      }
      return null;
    });


  }

}
