package ru.sergr972.restaurantvoting.web.vote;

import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.web.MatcherFactory;
import ru.sergr972.restaurantvoting.web.user.UserTestData;

import java.time.LocalDate;

import static ru.sergr972.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final Restaurant R1 = new Restaurant(RESTAURANT_ID, "ROSTIKS");
    public static final Restaurant R2 = new Restaurant(RESTAURANT_ID + 1, "VKUSNO");
    public static final Restaurant R3 = new Restaurant(RESTAURANT_ID + 2, "HACHOPURI");
    public static final Restaurant R4 = new Restaurant(RESTAURANT_ID + 3, "TOKIO");

    public static final Vote V2 = new Vote(2, LocalDate.of(2024, 1, 29), UserTestData.admin, R4);
    public static final Vote V4 = new Vote(4, LocalDate.of(2024, 1, 31), UserTestData.admin, R3);
    public static final Vote V5 = new Vote(5, LocalDate.now(), UserTestData.guest, R1);
    public static final Vote V6 = new Vote(6, LocalDate.now(), UserTestData.admin, R2);

    public static Vote getNewVote() {
        return new Vote(null, LocalDate.now(), UserTestData.user, R1);
    }
}
