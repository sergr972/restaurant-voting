package ru.sergr972.restaurantvoting.web.vote;

import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.web.MatcherFactory;
import ru.sergr972.restaurantvoting.web.user.UserTestData;

import java.time.LocalDate;

import static ru.sergr972.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "userId", "voteDate");

    public static final VoteTo V2 = new VoteTo(2, LocalDate.of(2024, 1, 29), UserTestData.ADMIN_ID, RESTAURANT_ID + 3);
    public static final VoteTo V4 = new VoteTo(4, LocalDate.of(2024, 1, 31), UserTestData.ADMIN_ID, RESTAURANT_ID + 2);
    public static final VoteTo V6 = new VoteTo(6, LocalDate.now(), UserTestData.ADMIN_ID, RESTAURANT_ID + 1);

    public static VoteTo getNewVote() {
        return new VoteTo(7, LocalDate.now(), UserTestData.USER_ID, RESTAURANT_ID);
    }

    public static VoteTo getUpdateVote() {
        return new VoteTo(6, LocalDate.now(), UserTestData.ADMIN_ID, RESTAURANT_ID + 3);
    }
}
