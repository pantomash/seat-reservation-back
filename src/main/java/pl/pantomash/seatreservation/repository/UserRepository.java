package pl.pantomash.seatreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pantomash.seatreservation.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
