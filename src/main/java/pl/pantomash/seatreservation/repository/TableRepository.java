package pl.pantomash.seatreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pantomash.seatreservation.domain.Table;

public interface TableRepository extends JpaRepository<Table, Long> {
}
