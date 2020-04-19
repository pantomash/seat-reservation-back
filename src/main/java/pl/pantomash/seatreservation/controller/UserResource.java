package pl.pantomash.seatreservation.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.pantomash.seatreservation.config.auth.AuthRequest;
import pl.pantomash.seatreservation.config.auth.jwt.JwtResponse;
import pl.pantomash.seatreservation.config.auth.jwt.JwtUtils;
import pl.pantomash.seatreservation.model.User;
import pl.pantomash.seatreservation.service.UserService;
import pl.pantomash.seatreservation.service.dto.UserDto;
import pl.pantomash.seatreservation.service.mapper.UserMapper;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserResource {
    private Logger log = LoggerFactory.getLogger(UserResource.class);
    private AuthenticationManager authManager;
    private final UserService userService;
    private final UserMapper mapper;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        authenticate(request.getLogin(), request.getPassword());

        User user = userService.findByLogin(request.getLogin());
        String jwt = jwtUtils.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("register")
    public void createUser(@Valid @RequestBody UserDto userDto) {
        userService.registerUser(mapper.toEntity(userDto));
    }

    private void authenticate(String login, String password) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (BadCredentialsException e) {
            throw new EntityNotFoundException("Wrong username or password!");
        }
    }
}
