package pl.com.bottega.dms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.model.users.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleObjectTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private TransactionTemplate tt;

  @Test
  public void savesUser() {
    UserFactory userFactory = new UserFactory();
    User admin = userFactory.adminUser("czesiek", "xxx");
    User supervisor = userFactory.supervisorUser("stefan", "xxx");
    tt.execute((c) -> {
      entityManager.persist(admin);
      entityManager.persist(supervisor);
      return null;
    });
    assertThat(entityManager.createQuery("SELECT count(r) FROM UserRole r").getSingleResult()).isEqualTo(3L);
    InvoiceCorrectorRole correctorRole = entityManager.find(InvoiceCorrectorRole.class, 1L);
    tt.execute((c) -> {
      User user = entityManager.find(UserCore.class, 1L);
      OrderCorrectorRole orderCorrectorRole = user.getRole(OrderCorrectorRole.class);
      orderCorrectorRole.orderCorrected("500");
      return null;
    });
  }

}
