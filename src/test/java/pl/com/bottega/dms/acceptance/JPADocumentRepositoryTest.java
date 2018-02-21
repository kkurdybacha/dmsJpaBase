package pl.com.bottega.dms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNotFoundException;
import pl.com.bottega.dms.model.DocumentRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JPADocumentRepositoryTest {

  @Autowired
  private DocumentRepository repository;

  @Autowired
  private TransactionTemplate tt;

  @Test
  public void savesNewDocument() {
    Document document = new Document();
    tt.execute(c -> {
      repository.save(document);
      return null;
    });
    Document documentRead = repository.get(document.getId());
    assertThat(documentRead).isNotNull();
  }

  @Test(expected = DocumentNotFoundException.class)
  public void throwsExceptionWhenIdIsInvalid() {
    repository.get(9999L);
  }

  @Test(expected = DocumentNotFoundException.class)
  public void removesDocument() {
    Document document = new Document();
    tt.execute(c -> {
      repository.save(document);
      return null;
    });

    tt.execute(c -> {
      repository.remove(document.getId());
      return null;
    });

    repository.get(document.getId());
  }

  @Test(expected = DocumentNotFoundException.class)
  public void throwsExceptionWhenRemovingNonExistingDocument() {
    repository.remove(99999L);
  }

}
