package pl.pantomash.seatreservation.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.domain.User;
import pl.pantomash.seatreservation.service.dto.UserDto;

@Mapper(componentModel = "spring", uses = {})
@Service
public interface UserMapper extends EntityMapper<UserDto, User> {

    User toEntity(UserDto userDto);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
