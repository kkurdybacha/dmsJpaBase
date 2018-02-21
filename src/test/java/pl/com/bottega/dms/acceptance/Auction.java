package pl.com.bottega.dms.acceptance;

import org.hibernate.annotations.BatchSize;
import pl.com.bottega.dms.model.PhoneNumber;

import javax.persistence.*;
import java.util.List;
import java.util.LinkedList;

@Entity
public class Auction {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne
  private Address address;

  @Embedded
  private PhoneNumber phoneNumber;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @OrderColumn
  @BatchSize(size = 10)
  public List<Bid> bids = new LinkedList<>();

  @PostLoad
  public void loaded() {
    System.out.print("a");
  }

}
