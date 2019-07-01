package pl.pantomash.seatreservation.controller;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pantomash.seatreservation.service.UserService;
import pl.pantomash.seatreservation.service.dto.UserDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserResource {
    private Logger log = LoggerFactory.getLogger(UserResource.class);
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("REST request to get all Users");
        List<UserDto> userDtoList = userService.findAll();
        if (userDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        log.debug("REST request to get User");
        UserDto userDto = userService.findOne(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws URISyntaxException {
        log.debug("REST request to create User");
        if (userDto.getId() != null) {
            return ResponseEntity.badRequest().header("A new User cannot have an ID").build();
        }
        UserDto result = userService.saveUser(userDto);
        return ResponseEntity.created(new URI("/user/" + result.getId()))
                .body(result);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws URISyntaxException {
        log.debug("REST request to update User");
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest().header("An existing User must have an ID").build();
        }
        UserDto result = userService.saveUser(userDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/user/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
