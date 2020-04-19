package pl.pantomash.seatreservation.config.auth;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
public class AuthRequest implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;

    @NotNull
    private String login;

    @NotNull
    @Size(min = 8, max = 32)
    private String password;

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
