package pl.pantomash.seatreservation.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pantomash.seatreservation.service.RestaurantService;
import pl.pantomash.seatreservation.service.dto.RestaurantDto;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RestaurantResource.class)
class RestaurantResourceTest {

    private Logger log = LoggerFactory.getLogger(RestaurantResource.class);

    @MockBean
    public RestaurantService restaurantService;

    @Autowired
    @InjectMocks
    public RestaurantResource restaurantResource;

    MockMvc mockMvc;

    List<RestaurantDto> restaurantDtoList;

    RestaurantDto dummyRestaurant1;
    RestaurantDto dummyRestaurantWithoutId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restaurantDtoList = new LinkedList<>();
        dummyRestaurantWithoutId = new RestaurantDto(null,"KFC", "Żory", "...");
        dummyRestaurant1 = new RestaurantDto(1L, "KFC", "Żory", "...");
        restaurantDtoList.add(dummyRestaurant1);
        restaurantDtoList.add(new RestaurantDto(2L, "MC Donalad", "Żory", "..."));

        mockMvc = MockMvcBuilders.standaloneSetup(restaurantResource).build();
    }

    @Test
    void getAllRestaurants() throws Exception {
        when(restaurantService.findAll()).thenReturn(restaurantDtoList);

        mockMvc.perform(get("/api/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getRestaurant() throws Exception {
        when(restaurantService.findOne(anyLong())).thenReturn(dummyRestaurant1);

        mockMvc.perform(get("/api/restaurant/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("KFC")));
    }

    @Test
    void createRestaurant() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyRestaurantWithoutId);

        mockMvc.perform(post("/api/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void updateRestaurant() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyRestaurant1);

        mockMvc.perform(put("/api/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRestaurant() throws Exception {
        restaurantService.deleteById(1L);
        verify(restaurantService, times(1)).deleteById(anyLong());
    }
}