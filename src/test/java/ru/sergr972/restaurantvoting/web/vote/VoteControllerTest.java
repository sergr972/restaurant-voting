package ru.sergr972.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.USER_ID;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static ru.sergr972.restaurantvoting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/api/votes";
    static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllVotesForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(V2, V3, V4));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllVotesByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(V5_MENU_DAY, V6_MENU_DAY));
    }
}