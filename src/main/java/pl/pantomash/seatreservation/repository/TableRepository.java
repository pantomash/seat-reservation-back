package pl.pantomash.seatreservation.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pantomash.seatreservation.domain.Table;

public interface TableRepository extends CrudRepository<Table, Long> {
}
