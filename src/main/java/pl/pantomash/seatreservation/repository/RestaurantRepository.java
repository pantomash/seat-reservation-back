package pl.pantomash.seatreservation.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pantomash.seatreservation.domain.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
