package ru.sergr972.restaurantvoting.web.data;

import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.web.MatcherFactory;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static ru.sergr972.restaurantvoting.web.data.RestaurantTestData.RESTAURANT_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "userId", "voteDate");

    public static final VoteTo V2 = new VoteTo(2, of(2024, 1, 29), UserTestData.ADMIN_ID, RESTAURANT_ID + 3);
    public static final VoteTo V4 = new VoteTo(4, of(2024, 1, 31), UserTestData.ADMIN_ID, RESTAURANT_ID + 2);
    public static final VoteTo V6 = new VoteTo(6, now(), UserTestData.ADMIN_ID, RESTAURANT_ID + 1);

}
