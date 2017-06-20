package pl.com.bottega.dms.infrastructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.NoSuchDocumentException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JPADocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TransactionTemplate tt;

    @Autowired
    private DBCleaner dbCleaner;

    @Before
    public void cleanDb() {
        dbCleaner.clean();
    }

    @Test
    public void savesNewDocuments() {
        // given
        String number = "11111";
        Document document = new Document();
        document.setTitle("test");
        document.setContent("test content");
        document.setNumber(number);

        // when
        tt.execute((c) -> {
            documentRepository.save(document);
            return null;
        });

        // then
        Document loadedDocument = tt.execute((c) -> documentRepository.load(number));
        assertThat(loadedDocument).isNotNull();
        assertThat(loadedDocument.getNumber()).isEqualTo(number);
    }

    @Test(expected = NoSuchDocumentException.class)
    public void throwsExceptionWhenDocumentDoesNotExist() {
        //when
        tt.execute((c) -> documentRepository.load("invalid number"));
    }

    @Test(expected = NoSuchDocumentException.class)
    public void removesDocument() {
        // given
        String number = "11111";
        Document document = new Document();
        document.setNumber(number);
        tt.execute((c) -> {
            documentRepository.save(document);
            return null;
        });

        // when
        tt.execute((c) -> {
            documentRepository.remove(number);
            return null;
        });

        //then
        tt.execute((c) -> documentRepository.load(number));

    }

    @Test
    public void canLoadAndSave() {
        // given
        String number = "11111";
        Document document = new Document();
        document.setNumber(number);
        tt.execute((c) -> {
            documentRepository.save(document);
            return null;
        });

        //when
        tt.execute((c) -> {
            Document doc = documentRepository.load(number);
            documentRepository.save(doc);
            return null;
        });

        // then
        // should be no exception
    }

}
