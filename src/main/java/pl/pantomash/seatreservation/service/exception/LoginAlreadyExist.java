package pl.pantomash.seatreservation.service.exception;

public class LoginAlreadyExist extends RuntimeException {
    public LoginAlreadyExist(String login) {
        super(String.format("User with given login: %s already exist", login));
    }
}
