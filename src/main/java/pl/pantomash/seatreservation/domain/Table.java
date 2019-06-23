package pl.pantomash.seatreservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@javax.persistence.Table(name = "tables")
public class Table extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "seats")
    private Integer seats;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
