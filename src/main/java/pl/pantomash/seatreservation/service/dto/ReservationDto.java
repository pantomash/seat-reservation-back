package pl.pantomash.seatreservation.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReservationDto {
    private Long id;
    private Integer people;
    private String fromDate;
    private String toDate;
    private Long userId;
    private Long tableId;
    private Long restaurantId;
}
