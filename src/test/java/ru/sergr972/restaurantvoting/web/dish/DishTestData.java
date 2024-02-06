package ru.sergr972.restaurantvoting.web.dish;

import ru.sergr972.restaurantvoting.model.Dish;
import ru.sergr972.restaurantvoting.web.MatcherFactory;
import ru.sergr972.restaurantvoting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "date", "restaurant");
    public static final Integer DISH_ID = 1;

    public static Dish d1 = new Dish(1, "Dish1", LocalDate.of(2024, 1, 31), 150);
    public static Dish d2 = new Dish(2, "Dish2", LocalDate.now(), 250);
    public static Dish d3 = new Dish(3, "Dish3", LocalDate.now(), 350);

    public static Dish getNewDish() {
        return new Dish(null, "NewDish", LocalDate.now(), 950, RestaurantTestData.R_1);
    }

    public static Dish getUpdatedDish() {
        return new Dish(DISH_ID, "UpdatedDish", LocalDate.now(), 951);
    }

}
