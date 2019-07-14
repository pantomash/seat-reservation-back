package pl.pantomash.seatreservation.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pantomash.seatreservation.domain.Restaurant;
import pl.pantomash.seatreservation.repository.RestaurantRepository;
import pl.pantomash.seatreservation.service.dto.RestaurantDto;
import pl.pantomash.seatreservation.service.mapper.RestaurantMapper;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    List<Restaurant> restaurantList;
    Restaurant dummyRestaurant1;
    RestaurantDto dummyRestaurantDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restaurantService = new RestaurantServiceImpl(restaurantRepository, restaurantMapper);
        restaurantList = new LinkedList<>();
        dummyRestaurant1 = new Restaurant(1L, "KFC", "Żory", "...");
        dummyRestaurantDto = new RestaurantDto(1L, "KFC", "Żory", "...");
        Restaurant dummyRestaurant2 = new Restaurant(2L, "MC DONALD", "Żory", "...");
        restaurantList.add(dummyRestaurant1);
        restaurantList.add(dummyRestaurant2);
    }

    @Test
    void findAll() {
        when(restaurantRepository.findAll()).thenReturn(restaurantList);
        List<RestaurantDto> result = restaurantService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findOne() {
        when(restaurantRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyRestaurant1));
        when(restaurantMapper.toDto(dummyRestaurant1)).thenReturn(dummyRestaurantDto);

        RestaurantDto result = restaurantService.findOne(dummyRestaurant1.getId());

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void saveRestaurant() {
        when(restaurantMapper.toEntity(dummyRestaurantDto)).thenReturn(dummyRestaurant1);
        when(restaurantMapper.toDto(dummyRestaurant1)).thenReturn(dummyRestaurantDto);
        when(restaurantRepository.save(dummyRestaurant1)).thenReturn(dummyRestaurant1);

        RestaurantDto result = restaurantService.saveRestaurant(dummyRestaurantDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteById() {
        restaurantService.deleteById(dummyRestaurantDto.getId());

        verify(restaurantRepository, times(1)).deleteById(dummyRestaurantDto.getId());
    }
}