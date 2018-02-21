import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.Employee;
import pl.com.bottega.dms.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class NoSpringApp {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DMS");
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    Document document = new Document();
    Document document1 = new Document();
    Document document2 = new Document();

    User user = new User();
    Employee employee = new Employee();
    user.setEmployee(employee);
    employee.setUser(user);
    document.setAuthor(employee);
    employee.addCreatedDocument(document);
    employee.addCreatedDocument(document1);
    employee.addCreatedDocument(document2);

    //em.persist(employee);
    em.persist(user);
    //em.persist(document);
    em.flush();


    List<Object[]> docs = em.createQuery("SELECT d, d FROM Document d").
        setMaxResults(2).setFirstResult(1).
        getResultList();

    Object[] t = docs.get(0);

    em.getTransaction().commit();
    emf.close();
  }

}
