package pl.pantomash.seatreservation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.domain.Reservation;
import pl.pantomash.seatreservation.repository.ReservationRepository;
import pl.pantomash.seatreservation.service.ReservationService;
import pl.pantomash.seatreservation.service.dto.ReservationDto;
import pl.pantomash.seatreservation.service.mapper.ReservationMapper;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public ReservationDto findOne(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return reservationMapper.toDto(reservation);
    }

    @Override
    public ReservationDto saveReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
