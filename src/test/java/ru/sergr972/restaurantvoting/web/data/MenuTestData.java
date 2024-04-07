package ru.sergr972.restaurantvoting.web.data;

import ru.sergr972.restaurantvoting.to.MenuTo;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

public class MenuTestData {
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class);

    public static final Integer DISH_ID = 1;

    public static MenuTo M1_TO = new MenuTo(1, "Dish1", of(2024, 1, 31), 150, 1);
    public static MenuTo M2_TO = new MenuTo(2, "Dish2", now(), 250, 1);
    public static MenuTo M3_TO = new MenuTo(3, "Dish3", now(), 350, 1);

    public static MenuTo getNewDish() {
        return new MenuTo(null, "NewDish", now(), 950, 1);
    }

    public static MenuTo getUpdatedDish() {
        return new MenuTo(DISH_ID, "UpdatedDish", now(), 951, 1);
    }
}
