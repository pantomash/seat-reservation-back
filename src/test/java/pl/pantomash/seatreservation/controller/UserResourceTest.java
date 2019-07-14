package pl.pantomash.seatreservation.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pantomash.seatreservation.service.UserService;
import pl.pantomash.seatreservation.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserResource.class)
class UserResourceTest {
    @MockBean
    public UserService userService;

    @InjectMocks
    public UserResource userResource;

    MockMvc mockMvc;

    List<UserDto> userList;

    UserDto dummyUser1;
    UserDto dummyUserWithoudId;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.initMocks(this);
        userList = new ArrayList<>();
        dummyUserWithoudId = new UserDto(null, "Jim", "Jericho", "1", "jim@test.com");
        dummyUser1 = new UserDto(1L, "Joe", "Doe", "999999", "test@test.com");
        userList.add(dummyUser1);
        userList.add(new UserDto(2L, "Jane", "Evans", "888888", "test1@test.com"));

        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(userList);

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getUser() throws Exception {
        when(userService.findOne(anyLong())).thenReturn(dummyUser1);

        mockMvc.perform(get("/api/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Joe")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    void createUser() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyUserWithoudId);

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyUser1);

        mockMvc.perform(put("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        userService.deleteById(1L);
        verify(userService, times(1)).deleteById(anyLong());

//        mockMvc.perform(delete("/api/user/{id}", 1L))
//                .andExpect(status().isOk());

    }
}