package pl.pantomash.seatreservation.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.domain.Restaurant;
import pl.pantomash.seatreservation.service.dto.RestaurantDto;

@Service
@Mapper(componentModel = "spring", uses = {})
public interface RestaurantMapper extends EntityMapper<RestaurantDto, Restaurant> {

    Restaurant toEntity(RestaurantDto restaurantDto);

    default Restaurant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}
