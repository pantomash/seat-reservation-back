package pl.pantomash.seatreservation.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.pantomash.seatreservation.domain.Reservation;
import pl.pantomash.seatreservation.domain.Restaurant;
import pl.pantomash.seatreservation.domain.Table;
import pl.pantomash.seatreservation.service.dto.ReservationDto;

@Mapper(componentModel = "spring", uses = {UserMapper.class, Table.class, Restaurant.class})
public interface ReservationMapper extends EntityMapper<ReservationDto, Reservation> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "table.id", target = "tableId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    ReservationDto toDto(Reservation reservation);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "tableId", target = "table")
    @Mapping(source = "restaurantId", target = "restaurant")
    Reservation toEntity(ReservationDto reservationDto);

    default Reservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return reservation;
    }
}
