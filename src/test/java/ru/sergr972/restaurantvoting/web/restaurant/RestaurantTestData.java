package ru.sergr972.restaurantvoting.web.restaurant;

import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");

//    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_ID = 1;

    public static final Restaurant r1 = new Restaurant(RESTAURANT_ID, "ROSTIKS");
    public static final Restaurant r2 = new Restaurant(RESTAURANT_ID + 1, "VKUSNO");
    public static final Restaurant r3 = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI");
    public static final Restaurant r4 = new Restaurant(RESTAURANT_ID + 3, "TOKIO");
}
