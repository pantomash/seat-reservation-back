package pl.pantomash.seatreservation.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pantomash.seatreservation.domain.User;
import pl.pantomash.seatreservation.repository.UserRepository;
import pl.pantomash.seatreservation.service.dto.UserDto;
import pl.pantomash.seatreservation.service.mapper.UserMapper;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    List<User> userList;
    User dummyUser1;
    UserDto dummyUserDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, userMapper);
        userList = new LinkedList<>();
        dummyUser1 = new User(1L, "Joe", "Doe", "999999", "test@test.com");
        dummyUserDto = new UserDto(1L, "Joe", "Doe", "999999", "test@test.com");
        User dummyUser2 = new User(2L, "Jane", "Evans", "888888", "test1@test.com");
        userList.add(dummyUser1);
        userList.add(dummyUser2);
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(userList);
        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findOne() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyUser1));
        when(userMapper.toDto(dummyUser1)).thenReturn(dummyUserDto);

        UserDto result = userService.findOne(dummyUser1.getId());

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void saveUser() {
        when(userMapper.toEntity(dummyUserDto)).thenReturn(dummyUser1);
        when(userMapper.toDto(dummyUser1)).thenReturn(dummyUserDto);
        when(userRepository.save(dummyUser1)).thenReturn(dummyUser1);

        UserDto result = userService.saveUser(dummyUserDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteById() {
        userService.deleteById(dummyUserDto.getId());

        verify(userRepository, times(1)).deleteById(dummyUserDto.getId());
    }
}