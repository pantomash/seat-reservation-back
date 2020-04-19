package pl.pantomash.seatreservation.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.model.User;
import pl.pantomash.seatreservation.service.UserService;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private final UserService userService;

    public UserPrincipalDetailsService(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String login) {
        User user = this.userService.findByLogin(login);
        return new UserPrincipal(user);
    }
}
