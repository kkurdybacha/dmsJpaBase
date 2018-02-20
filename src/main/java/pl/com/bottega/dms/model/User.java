package pl.com.bottega.dms.model;

import javax.persistence.*;

@Entity
public class User {

  @Id
  private Long employeeId;

  private String login;

  private String password;

  @OneToOne(optional = false)
  @JoinColumn(name = "employeeId")
  @MapsId
  private Employee employee;

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
}
