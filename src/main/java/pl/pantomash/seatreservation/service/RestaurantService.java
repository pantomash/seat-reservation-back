package pl.pantomash.seatreservation.service;

import pl.pantomash.seatreservation.service.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> findAll();
    RestaurantDto findOne(Long id);
    RestaurantDto saveRestaurant(RestaurantDto restaurantDto);
    void deleteById(Long id);
}
