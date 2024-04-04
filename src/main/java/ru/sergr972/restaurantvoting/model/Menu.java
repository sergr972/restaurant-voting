package ru.sergr972.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "menu_item"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name"}
        , name = "menu_item_unique_name_idx"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Menu extends NamedEntity {

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "dish_date", nullable = false)
    @NotNull
    @ToString.Exclude
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ToString.Exclude
    private Restaurant restaurant;

    public Menu(String name, LocalDate date, int price, Restaurant restaurant) {
        this(null, name, date, price, restaurant);
    }

    public Menu(Integer id, String name, LocalDate date, Integer price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public Menu(Integer id, String name, LocalDate date, Integer price, Restaurant restaurant) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }
}
