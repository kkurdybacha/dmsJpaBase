package pl.com.bottega.dms.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.Employee;

import javax.transaction.Transactional;

@Component
@Transactional
public class StandardDocumentFlowProcess implements DocumentFlowProcess {

  private DocumentRepository documentRepository;
  private EmployeeRepository employeeRepository;

  public StandardDocumentFlowProcess(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public Long create(String title, Long creatorId) {
    Employee employee = employeeRepository.load(creatorId);

    employee.checkCreateDocumentPermission();
    Document document = new Document(title, creatorId);

    documentRepository.save(document);
    return document.id();
  }

  @Override
  public void edit(Long documentId, String title, String content, Long editorId) {
    Document document = documentRepository.load(documentId);
    Employee employee = employeeRepository.load(editorId);

    employee.checkEditDocumentPermission();
    document.edit(title, content, editorId);

    documentRepository.save(document);
  }

  @Override
  public void verify(Long documentId, Long verifierId) {

  }

  @Override
  public void publish(Long documentId, Long publisherId, Long departmentId) {

  }

  @Override
  public void archive(Long documentId, Long archiverId) {

  }
}
