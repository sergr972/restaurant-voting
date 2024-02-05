package ru.sergr972.restaurantvoting.web.restaurant;

import ru.sergr972.restaurantvoting.model.Dish;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Set;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");

    public static final int RESTAURANT_ID = 1;

    public static final Restaurant r1 = new Restaurant(RESTAURANT_ID, "ROSTIKS",
            Set.of(new Dish(1, "Dish1", LocalDate.of(2024, 1, 31), 150),
                    new Dish(2, "Dish2", LocalDate.now(), 250),
                    new Dish(3, "Dish3", LocalDate.now(), 350)));
    public static final Restaurant r2 = new Restaurant(RESTAURANT_ID + 1, "VKUSNO", null);
    public static final Restaurant r3 = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI", null);
    public static final Restaurant r4 = new Restaurant(RESTAURANT_ID + 3, "TOKIO", null);

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant");
    }
}
