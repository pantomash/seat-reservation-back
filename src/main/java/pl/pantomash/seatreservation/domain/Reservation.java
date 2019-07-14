package pl.pantomash.seatreservation.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@javax.persistence.Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "people")
    private Integer people;

    @Column(name = "fromdate")
    private LocalDate fromDate;

    @Column(name = "todate")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public String getFromDate() {
        if (this.fromDate != null) {
            return this.fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return null;
        }
    }

    public void setFromDate(String fromDate) {
        if (fromDate != null) {
            this.fromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            this.fromDate = null;
        }
    }

    public String getToDate() {
        if (this.toDate != null) {
            return this.toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return null;
        }
    }

    public void setToDate(String toDate) {
        if (toDate != null) {
            this.toDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            this.toDate = null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
