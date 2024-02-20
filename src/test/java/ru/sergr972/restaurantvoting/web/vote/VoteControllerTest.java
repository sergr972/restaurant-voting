package ru.sergr972.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.util.JsonUtil;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.*;
import static ru.sergr972.restaurantvoting.web.vote.VoteController.REST_URL;
import static ru.sergr972.restaurantvoting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    static final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    VoteRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllVotesForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/users/" + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(V2, V4, V6));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllVotesByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(V5, V6));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        Vote newVote = getNewVote();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + getNewVote().getRestaurant().id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);

        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newVote);
    }
}