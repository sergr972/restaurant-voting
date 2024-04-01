package ru.sergr972.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sergr972.restaurantvoting.model.NamedEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuTo extends NamedEntity {

    private int price;

    private LocalDate date;

    private Integer restaurantId;

    public MenuTo(Integer id, String name, LocalDate date, int price, Integer restaurantId) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurantId = restaurantId;
    }
}
