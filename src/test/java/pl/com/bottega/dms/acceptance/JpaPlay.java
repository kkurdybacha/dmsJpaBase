package pl.com.bottega.dms.acceptance;

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

import javax.persistence.EntityManager;

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

  @Test
  public void cantLazyLoadOutsidePersistenceContext() {
    Auction auction = new Auction();

    transactionTemplate.execute(c -> {
      entityManager.persist(auction);
      return null;
    });

    Auction auction1 = entityManager.find(Auction.class, 1L);
    auction1.bids.size();
  }

}
