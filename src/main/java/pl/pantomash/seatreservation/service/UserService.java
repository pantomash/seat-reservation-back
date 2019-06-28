package pl.pantomash.seatreservation.service;

import pl.pantomash.seatreservation.service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findOne(Long id);
    UserDto saveUser(UserDto userDto);
    void deleteById(Long id);
}
