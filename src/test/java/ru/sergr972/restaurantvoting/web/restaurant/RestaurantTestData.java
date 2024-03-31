package ru.sergr972.restaurantvoting.web.restaurant;

import ru.sergr972.restaurantvoting.model.Menu;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.to.RestaurantTo;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final Integer RESTAURANT_ID = 1;

    public static final RestaurantTo R_1_TO = new RestaurantTo(RESTAURANT_ID, "ROSTIKS");

    public static final RestaurantTo R_2_TO = new RestaurantTo(RESTAURANT_ID + 1, "VKUSNO");

    public static final RestaurantTo R_3_TO = new RestaurantTo(RESTAURANT_ID + 2, "HACHOPURI");

    public static final RestaurantTo R_4_TO = new RestaurantTo(RESTAURANT_ID + 3, "TOKIO");

    public static final Restaurant R_1 = new Restaurant(RESTAURANT_ID, "ROSTIKS",
            List.of(new Menu(1, "Dish1", LocalDate.of(2024, 1, 31), 150),
                    new Menu(2, "Dish2", LocalDate.now(), 250),
                    new Menu(3, "Dish3", LocalDate.now(), 350)));

    public static final Restaurant R_4 = new Restaurant(RESTAURANT_ID + 3, "TOKIO",
            List.of(new Menu(10, "Dish1", LocalDate.of(2024, 1, 28), 250),
                    new Menu(11, "Dish2", LocalDate.now(), 450),
                    new Menu(12, "Dish3", LocalDate.now(), 650)));

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant");
    }
}
