package pl.pantomash.seatreservation.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pantomash.seatreservation.domain.Reservation;
import pl.pantomash.seatreservation.domain.Restaurant;
import pl.pantomash.seatreservation.domain.Table;
import pl.pantomash.seatreservation.domain.User;
import pl.pantomash.seatreservation.repository.ReservationRepository;
import pl.pantomash.seatreservation.service.dto.ReservationDto;
import pl.pantomash.seatreservation.service.mapper.ReservationMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    List<Reservation> reservationList;
    Reservation dummyReservation;
    ReservationDto dummyReservationDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationServiceImpl(reservationRepository, reservationMapper);
        reservationList = new LinkedList<>();
        User user = new User(1L, "Joe", "Doe", "999999", "test@test.com");
        Restaurant restaurant = new Restaurant(5L, "KFC", "Żory", "...");
        Table table = new Table(2L, "Stolik 2", 2, restaurant);



        dummyReservation = new Reservation(1L,
                1,
                LocalDate.parse("2019-04-21 17:00", dateTimeFormatter),
                LocalDate.parse("2019-04-21 18:00", dateTimeFormatter),
                user, table, restaurant);
        Reservation dummyReservation2 = new Reservation(2L,
                1,
                LocalDate.parse("2019-04-21 17:00", dateTimeFormatter),
                LocalDate.parse("2019-04-21 18:00", dateTimeFormatter),
                user, table, restaurant);
        dummyReservationDto = new ReservationDto(1L,
                1,
                "2019-04-21 17:00", "2019-04-21 18:00",
                user.getId(), table.getId(), restaurant.getId());

        reservationList.add(dummyReservation);
        reservationList.add(dummyReservation2);
    }

    @Test
    void findAll() {
        when(reservationRepository.findAll()).thenReturn(reservationList);
        List<ReservationDto> result = reservationService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findOne() {
        when(reservationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyReservation));
        when(reservationMapper.toDto(dummyReservation)).thenReturn(dummyReservationDto);

        ReservationDto result = reservationService.findOne(dummyReservation.getId());

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void saveReservation() {
        when(reservationMapper.toEntity(dummyReservationDto)).thenReturn(dummyReservation);
        when(reservationMapper.toDto(dummyReservation)).thenReturn(dummyReservationDto);
        when(reservationRepository.save(dummyReservation)).thenReturn(dummyReservation);

        ReservationDto result = reservationService.saveReservation(dummyReservationDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteById() {
        reservationService.deleteById(dummyReservationDto.getId());

        verify(reservationRepository, times(1)).deleteById(dummyReservationDto.getId());
    }
}