package pl.pantomash.seatreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pantomash.seatreservation.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
