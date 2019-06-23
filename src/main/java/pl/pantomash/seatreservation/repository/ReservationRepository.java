package pl.pantomash.seatreservation.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pantomash.seatreservation.domain.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
