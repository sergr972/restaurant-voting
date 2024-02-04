package ru.sergr972.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Dish> menu;

    public Restaurant(String name) {
        super(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Set<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
