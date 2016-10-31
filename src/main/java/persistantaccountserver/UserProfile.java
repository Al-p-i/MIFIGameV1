package persistantaccountserver;

import org.hibernate.annotations.GenericGenerator;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * @author a.pomosov
 */
@Entity
@Table(name = "ACCOUNTS")
public class UserProfile {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public UserProfile(){}

    public UserProfile(@NotNull String login, @NotNull String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
