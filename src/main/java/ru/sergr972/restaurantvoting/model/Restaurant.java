package ru.sergr972.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "restaurant"
        , uniqueConstraints = @UniqueConstraint(columnNames = "name"
        , name = "restaurants_unique_name_idx"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Menu> menu;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Menu> menu) {
        super(id, name);
        this.menu = menu;
    }
}
