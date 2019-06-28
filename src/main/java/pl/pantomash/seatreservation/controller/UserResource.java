package pl.pantomash.seatreservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import pl.pantomash.seatreservation.service.UserService;

@RestController
public class UserResource {
    private Logger log = LoggerFactory.getLogger(UserResource.class);
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }


}
