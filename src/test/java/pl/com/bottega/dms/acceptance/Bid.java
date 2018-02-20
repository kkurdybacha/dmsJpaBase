package pl.com.bottega.dms.acceptance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bid {

  @Id
  @GeneratedValue
  public Long id;

}
