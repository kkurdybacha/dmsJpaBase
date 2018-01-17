package pl.com.bottega.dms.model.users;


import javax.persistence.*;
import java.time.Clock;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserRole implements User {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserCore userCore;

    @Override
    public <T extends UserRole> T getRole(Class<T> roleClass) {
        return userCore.getRole(roleClass);
    }

    @Override
    public void addRole(UserRole userRole) {
        userCore.addRole(userRole);
    }

    @Override
    public void changePassword(String newPassword) {
        userCore.changePassword(newPassword);
    }

    @Override
    public void saveLastLoginDate(Clock clock) {
        userCore.saveLastLoginDate(clock);
    }

    void setUserCore(UserCore userCore) {
        this.userCore = userCore;
    }
}
