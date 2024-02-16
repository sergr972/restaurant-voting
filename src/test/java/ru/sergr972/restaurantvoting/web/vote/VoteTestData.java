package ru.sergr972.restaurantvoting.web.vote;

import ru.sergr972.restaurantvoting.model.Dish;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.web.MatcherFactory;
import ru.sergr972.restaurantvoting.web.user.UserTestData;

import java.time.LocalDate;
import java.util.Set;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static ru.sergr972.restaurantvoting.web.restaurant.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final Restaurant R2_VOTE = new Restaurant(RESTAURANT_ID + 3, "TOKIO",
            Set.of(new Dish(10, "Dish1", LocalDate.of(2024, 1, 28), 250)));

    public static final Restaurant R3_VOTE = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI",
            Set.of(new Dish(7, "Dish1", LocalDate.of(2024, 1, 30), 350)));

    public static final Restaurant R4_VOTE = R2_MENU_DAY;

    public static Vote V2 = new Vote(1, of(2024, 1, 28), UserTestData.user, R2_VOTE);
    public static Vote V3 = new Vote(3, of(2024, 1, 30), UserTestData.user, R3_VOTE);
    public static Vote V4 = new Vote(5, now(), UserTestData.user, R4_VOTE);

    public static Vote V5_MENU_DAY = new Vote(5, now(), UserTestData.user, R2_MENU_DAY);
    public static Vote V6_MENU_DAY = new Vote(6, now(), UserTestData.admin, R3_MENU_DAY);
}
