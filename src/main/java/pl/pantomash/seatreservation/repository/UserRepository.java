package pl.pantomash.seatreservation.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pantomash.seatreservation.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
