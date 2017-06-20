package pl.com.bottega.dms.model;

public interface DocumentRepository {


    void save(Document document);

    Document load(String number);

    void remove(String number);
}
