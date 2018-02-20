package pl.com.bottega.dms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.model.DocumentRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JPADocumentRepositoryTest {

  @Autowired
  private DocumentRepository repository;

  @Autowired
  private TransactionTemplate tt;

  @Test
  public void savesNewDocument() {

  }

  @Test
  public void removesDocument() {

  }

}
