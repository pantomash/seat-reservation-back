package pl.pantomash.seatreservation.service;

import pl.pantomash.seatreservation.service.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> findAll();
    ReservationDto findOne(Long id);
    ReservationDto saveReservation(ReservationDto reservationDto);
    void deleteById(Long id);
}
