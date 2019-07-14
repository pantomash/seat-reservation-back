package pl.pantomash.seatreservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pantomash.seatreservation.service.ReservationService;
import pl.pantomash.seatreservation.service.dto.ReservationDto;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReservationResource {
    private Logger log = LoggerFactory.getLogger(ReservationResource.class);
    private final ReservationService reservationService;

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservation")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        log.debug("REST request to get all Reservations");
        List<ReservationDto> reservationDtoList = reservationService.findAll();
        if (reservationDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/reservation/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation");
        ReservationDto reservationDto = reservationService.findOne(id);
        if (reservationDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationDto);
    }

    @PostMapping(value = "/reservation")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) throws URISyntaxException {
        log.debug("REST request to create Reservation");
        if (reservationDto.getId() != null) {
            return ResponseEntity.badRequest().header("A new Reservation cannot already have an ID").build();
        }
        ReservationDto result = reservationService.saveReservation(reservationDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/reservation")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto) throws URISyntaxException {
        log.debug("REST request to update Reservation");
        if (reservationDto.getId() == null) {
            return ResponseEntity.badRequest().header("An existing Reservation must have an ID").build();
        }
        ReservationDto result = reservationService.saveReservation(reservationDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
