package pl.pantomash.seatreservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "info")
    private String info;
}
