package ru.sergr972.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.mapper.VoteMapper;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.util.JsonUtil;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.util.TimeUtil.setTime;
import static ru.sergr972.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.*;
import static ru.sergr972.restaurantvoting.web.vote.VoteController.REST_URL;
import static ru.sergr972.restaurantvoting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    private final VoteRepository repository;
    private final VoteMapper voteMapper;

    @Autowired
    VoteControllerTest(VoteRepository repository, VoteMapper voteMapper) {
        this.repository = repository;
        this.voteMapper = voteMapper;
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllVotesForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/users/" + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(V2, V4, V6));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllVotesByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(V5, V6));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        VoteTo newVote = getNewVote();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + getNewVote().getRestaurantId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);

        int newId = created.id();
        newVote.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVote);
        VOTE_TO_MATCHER.assertMatch(repository.findById(newId)
                        .stream()
                        .map(voteMapper::toTo)
                        .collect(Collectors.toList())
                , newVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        setTime("2024-03-12T10:00:00Z");
        VoteTo updatedVote = getUpdateVote();
        perform(MockMvcRequestBuilders.put(REST_URL + "/restaurants/" + getUpdateVote().getRestaurantId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedVote)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_TO_MATCHER.assertMatch(repository.getVoteByUserAndVoteDate(admin, LocalDate.now())
                        .stream()
                        .map(voteMapper::toTo)
                        .collect(Collectors.toList())
                , updatedVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateAfterEndOfVoteTime() throws Exception {
        setTime("2024-03-12T12:00:00Z");
        VoteTo updatedVote = getUpdateVote();
        perform(MockMvcRequestBuilders.put(REST_URL + "/restaurants/" + getUpdateVote().getRestaurantId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedVote)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createInvalid() throws Exception {
        VoteTo createTo = new VoteTo(7, LocalDate.now(), USER_ID, RESTAURANT_ID + 4);
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RESTAURANT_ID + 4)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createTo)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        VoteTo updatedTo = new VoteTo(6, LocalDate.now(), ADMIN_ID, RESTAURANT_ID + 4);
        perform(MockMvcRequestBuilders.put(REST_URL + "/restaurants/" + RESTAURANT_ID + 4)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}