package ru.sergr972.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 100000)
    private int price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate date, int price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public Dish(Integer id, String name, LocalDate date, int price, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
        this.date = date;
    }
}
