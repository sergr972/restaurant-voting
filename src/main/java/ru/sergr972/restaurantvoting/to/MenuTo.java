package ru.sergr972.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sergr972.restaurantvoting.model.NamedEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo extends NamedEntity {

    private int price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    private Integer restaurantId;

    public MenuTo(Integer id, String name, LocalDate date, int price, Integer restaurantId) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurantId = restaurantId;
    }
}
