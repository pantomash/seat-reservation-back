package pl.pantomash.seatreservation.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pantomash.seatreservation.service.ReservationService;
import pl.pantomash.seatreservation.service.dto.ReservationDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 @ExtendWith(MockitoExtension.class)
@WebMvcTest(ReservationResource.class)
class ReservationResourceTest {

    @MockBean
    public ReservationService reservationService;

    @Autowired
    @InjectMocks
    public ReservationResource reservationResource;

    MockMvc mockMvc;

    List<ReservationDto> reservationDtoList;

    ReservationDto dummyReservation1;
    ReservationDto dummyReservationWithoutId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationDtoList = new ArrayList<>();
        dummyReservationWithoutId = new ReservationDto(null, 1, "2019-04-21 15:00", "2019-04-21 16:00", 1L, 1L, 1L);
        dummyReservation1 = new ReservationDto(1L, 1, "2019-04-21 17:00", "2019-04-21 18:00", 1L, 1L, 1L);
        reservationDtoList.add(dummyReservation1);
        reservationDtoList.add(new ReservationDto(2L, 1, "2019-08-12 07:00", "2019-08-12 09:00", 1L, 1L, 1L));

        mockMvc = MockMvcBuilders.standaloneSetup(reservationResource).build();
    }

    @Test
    void getAllReservations() throws Exception {
        when(reservationService.findAll()).thenReturn(reservationDtoList);

        mockMvc.perform(get("/api/reservation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getReservation() throws Exception {
        when(reservationService.findOne(anyLong())).thenReturn(dummyReservation1);

        mockMvc.perform(get("/api/reservation/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.people", is(1)));
    }

    @Test
    void createReservation() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyReservationWithoutId);

        mockMvc.perform(post("/api/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void updateReservation() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyReservation1);

        mockMvc.perform(put("/api/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReservation() throws Exception {
        reservationService.deleteById(1L);
        verify(reservationService, times(1)).deleteById(anyLong());
    }
}