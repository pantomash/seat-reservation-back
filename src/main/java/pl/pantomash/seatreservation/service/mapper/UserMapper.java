package pl.pantomash.seatreservation.service.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.model.User;
import pl.pantomash.seatreservation.service.dto.UserDto;
import java.util.List;

@Service
public class UserMapper implements EntityMapper<UserDto, User> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto toDto(User user) { return null; };

    @Override
    public User toEntity(UserDto userDto) {
        return new User(
                userDto.getLogin(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhoneNumber(),
                userDto.getEmail()
        );
    };

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        return null;
    }

    @Override
    public List<UserDto> toDto(List<User> entityList) {
        return null;
    }
}
