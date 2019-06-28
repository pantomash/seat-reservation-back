package pl.pantomash.seatreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pantomash.seatreservation.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
