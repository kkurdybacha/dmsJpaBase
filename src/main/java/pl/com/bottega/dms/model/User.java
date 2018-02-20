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

  public Long getId() {
    return employeeId;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getLogin() {
    return login;
  }

  public void setId(long id) {
    this.employeeId = id;
  }
}
