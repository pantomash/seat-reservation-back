package pl.pantomash.seatreservation.service.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReservationDto {
    private Long id;
    private Integer people;
    private Date fromDate;
    private Date toDate;
    private Long userId;
    private Long tableId;
    private Long restaurantId;
}
