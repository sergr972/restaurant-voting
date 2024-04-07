package ru.sergr972.restaurantvoting.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;
import ru.sergr972.restaurantvoting.web.data.RestaurantTestData;
import ru.sergr972.restaurantvoting.web.data.VoteTestData;

import java.time.LocalDate;

import static ru.sergr972.restaurantvoting.web.data.UserTestData.ADMIN_MAIL;
import static ru.sergr972.restaurantvoting.web.data.UserTestData.admin;
import static ru.sergr972.restaurantvoting.web.data.VoteTestData.V2;

class VoteMapperTest extends AbstractControllerTest {

    private final VoteMapper voteMapper;

    @Autowired
    VoteMapperTest(VoteMapper voteMapper) {
        this.voteMapper = voteMapper;
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void setVoteMapper() {
        Vote vote = new Vote();
        vote.setId(2);
        vote.setUser(admin);
        vote.setVoteDate(LocalDate.of(2024, 1, 29));
        vote.setRestaurant(RestaurantTestData.R_4_MENU);
        VoteTo voteTo = voteMapper.toTo(vote);
        VoteTestData.VOTE_TO_MATCHER.assertMatch(voteTo,V2);
    }
}