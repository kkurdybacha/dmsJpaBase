package pl.com.bottega.dms.acceptance;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Auction {

  @Id
  @GeneratedValue
  public Long id;

  @OneToMany(cascade = CascadeType.ALL)
  public Collection<Bid> bids = new LinkedList<>();

}
