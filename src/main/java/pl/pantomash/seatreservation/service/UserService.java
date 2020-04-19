package pl.pantomash.seatreservation.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.model.User;
import pl.pantomash.seatreservation.repository.UserRepository;
import pl.pantomash.seatreservation.service.dto.UserDto;
import pl.pantomash.seatreservation.service.exception.LoginAlreadyExist;
import pl.pantomash.seatreservation.service.exception.UserNotFound;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Transactional
    public void registerUser(User user) {
        if (isUsernameUnique(user)) {
            userRepository.save(user);
        }
    }

    @Transactional
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFound(login));
    }

    private boolean isUsernameUnique(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new LoginAlreadyExist(user.getLogin());
        }
        return true;
    }


}
