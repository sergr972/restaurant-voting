package ru.sergr972.restaurantvoting.web.data;

import ru.sergr972.restaurantvoting.model.Menu;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.to.RestaurantTo;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

import java.util.List;

import static java.time.LocalDate.now;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final Integer RESTAURANT_ID = 1;

    public static final RestaurantTo R_1_TO = new RestaurantTo(RESTAURANT_ID, "ROSTIKS");

    public static final RestaurantTo R_2_TO = new RestaurantTo(RESTAURANT_ID + 1, "VKUSNO");

    public static final RestaurantTo R_3_TO = new RestaurantTo(RESTAURANT_ID + 2, "HACHOPURI");

    public static final RestaurantTo R_4_TO = new RestaurantTo(RESTAURANT_ID + 3, "TOKIO");

    public static final Restaurant R_1_MENU = new Restaurant(RESTAURANT_ID, "ROSTIKS",
            List.of(new Menu(2, "Dish2", now(), 250),
                    new Menu(3, "Dish3", now(), 350)));

    public static final Restaurant R_2_MENU = new Restaurant(RESTAURANT_ID + 1, "VKUSNO",
            List.of(new Menu(5, "Dish2", now(), 350),
                    new Menu(6, "Dish3", now(), 450)));

    public static final Restaurant R_3_MENU = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI",
            List.of(new Menu(8, "Dish2", now(), 450),
                    new Menu(9, "Dish3", now(), 550)));

    public static final Restaurant R_4_MENU = new Restaurant(RESTAURANT_ID + 3, "TOKIO",
            List.of(new Menu(11, "Dish2", now(), 450),
                    new Menu(12, "Dish3", now(), 650)));

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant");
    }
}
