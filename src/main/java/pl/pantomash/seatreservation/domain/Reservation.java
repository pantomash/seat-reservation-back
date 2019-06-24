package pl.pantomash.seatreservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@javax.persistence.Table(name = "reservations")
public class Reservation extends BaseEntity {
    @Column(name = "people")
    private Integer people;

    @Column(name = "fromdate")
    private Date fromDate;

    @Column(name = "todate")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
