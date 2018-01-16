package pl.com.bottega.dms.model;

public interface DocumentRepository {

  void save(Document document);

  Document load(Long documentId);

}
