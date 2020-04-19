package pl.pantomash.seatreservation.service.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String login) {
        super(String.format("User with login : %s not found", login));
    }
}
