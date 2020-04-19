package pl.pantomash.seatreservation.service.exception;

public class LoginNotFound extends RuntimeException {
    public LoginNotFound() {
        super("Cannot get login of logged user");
    }
}
