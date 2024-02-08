package ru.sergr972.restaurantvoting.web.restaurant;

import ru.sergr972.restaurantvoting.model.Dish;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Set;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT_ID = 1;

    public static final Restaurant R_1 = new Restaurant(RESTAURANT_ID, "ROSTIKS",
            Set.of(new Dish(1, "Dish1", LocalDate.of(2024, 1, 31), 150),
                    new Dish(2, "Dish2", LocalDate.now(), 250),
                    new Dish(3, "Dish3", LocalDate.now(), 350)));

    public static final Restaurant R_2 = new Restaurant(RESTAURANT_ID + 1, "VKUSNO",
            Set.of(new Dish(4, "Dish1", LocalDate.of(2024, 1, 29), 250),
                    new Dish(5, "Dish2", LocalDate.now(), 350),
                    new Dish(6, "Dish3", LocalDate.now(), 450)));

    public static final Restaurant R_3 = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI",
            Set.of(new Dish(7, "Dish1", LocalDate.of(2024, 1, 30), 350),
                    new Dish(8, "Dish2", LocalDate.now(), 450),
                    new Dish(9, "Dish3", LocalDate.now(), 550)));

    public static final Restaurant R_4 = new Restaurant(RESTAURANT_ID + 3, "TOKIO",
            Set.of(new Dish(10, "Dish1", LocalDate.of(2024, 1, 28), 250),
                    new Dish(11, "Dish2", LocalDate.now(), 450),
                    new Dish(12, "Dish3", LocalDate.now(), 650)));

    public static final Restaurant R1_MENU_DAY = new Restaurant(RESTAURANT_ID, "ROSTIKS",
            Set.of(new Dish(2, "Dish2", LocalDate.now(), 250),
                    new Dish(3, "Dish3", LocalDate.now(), 350)));

    public static final Restaurant R2_MENU_DAY = new Restaurant(RESTAURANT_ID + 1, "VKUSNO",
            Set.of(new Dish(5, "Dish2", LocalDate.now(), 350),
                    new Dish(6, "Dish3", LocalDate.now(), 450)));

    public static final Restaurant R3_MENU_DAY = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI",
            Set.of(new Dish(8, "Dish2", LocalDate.now(), 450),
                    new Dish(9, "Dish3", LocalDate.now(), 550)));

    public static final Restaurant R4_MENU_DAY = new Restaurant(RESTAURANT_ID + 3, "TOKIO",
            Set.of(new Dish(11, "Dish2", LocalDate.now(), 450),
                    new Dish(12, "Dish3", LocalDate.now(), 650)));


    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant");
    }
}
