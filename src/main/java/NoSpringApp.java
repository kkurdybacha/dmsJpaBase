import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.Employee;
import pl.com.bottega.dms.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class NoSpringApp {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DMS");
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    Document document = new Document();

    User user = new User();
    Employee employee = new Employee();
    user.setEmployee(employee);
    employee.setUser(user);
    document.setAuthor(employee);
    employee.addCreatedDocument(document);

    em.persist(employee);
    em.persist(user);
    em.persist(document);
    em.flush();
    System.out.println(document.getId());
    em.getTransaction().commit();
    emf.close();
  }

}
