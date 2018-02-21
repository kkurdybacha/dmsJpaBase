package pl.com.bottega.dms.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentRepository;


@Component
public class StandardDocumentFlowProcess implements DocumentFlowProcess {

  @Autowired
  private DocumentRepository documentRepository;

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Long createDocument() {
    Document document = new Document();
    documentRepository.save(document);
    return document.getId();
  }

  @Override
  public void verifyDocument(Long documentId) {
    Document document = documentRepository.get(documentId);
    document.verify();
    documentRepository.save(document);
  }


}
