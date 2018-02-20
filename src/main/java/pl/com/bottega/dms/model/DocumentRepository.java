package pl.com.bottega.dms.model;

public interface DocumentRepository {

  Document get(Long id) throws DocumentNotFoundException;

  void save(Document document);

  void remove(Long id) throws DocumentNotFoundException;

}
