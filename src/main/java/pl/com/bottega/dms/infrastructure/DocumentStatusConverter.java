package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.model.DocumentStatus;

import javax.persistence.AttributeConverter;

public class DocumentStatusConverter implements AttributeConverter<DocumentStatus, String> {
  @Override
  public String convertToDatabaseColumn(DocumentStatus attribute) {
    return attribute.name().substring(0, 1);
  }

  @Override
  public DocumentStatus convertToEntityAttribute(String dbData) {
    for(DocumentStatus status : DocumentStatus.values()) {
      if(status.name().startsWith(dbData))
        return status;
    }
    throw new IllegalArgumentException("Invalid value from db");
  }
}
