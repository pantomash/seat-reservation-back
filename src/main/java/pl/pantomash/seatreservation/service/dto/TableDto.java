package pl.pantomash.seatreservation.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TableDto {
    private Long id;
    private String name;
    private Integer seats;
    private Long restaurantId;
}
