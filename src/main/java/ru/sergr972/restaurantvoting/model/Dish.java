package ru.sergr972.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.sergr972.restaurantvoting.validation.NoHtml;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity{

    @Column(name = "price", nullable = false)
    @Range(min = 100, max = 100_000_00)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", menu=" + menu +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
