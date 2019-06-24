package pl.pantomash.seatreservation.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RestaurantDto {
    private Long id;
    private String name;
    private String address;
    private String info;
}
