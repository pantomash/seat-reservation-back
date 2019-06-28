package pl.pantomash.seatreservation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.domain.User;
import pl.pantomash.seatreservation.repository.UserRepository;
import pl.pantomash.seatreservation.service.UserService;
import pl.pantomash.seatreservation.service.dto.UserDto;
import pl.pantomash.seatreservation.service.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public UserDto findOne(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
