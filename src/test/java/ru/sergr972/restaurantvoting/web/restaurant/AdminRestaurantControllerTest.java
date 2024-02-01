package ru.sergr972.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.TestUtil.userHttpBasic;
import static ru.sergr972.restaurantvoting.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.sergr972.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.user;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(r1));
    }

//    @Test
//    void getAll() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL)
//                .with(userHttpBasic(user)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(TO_MATCHER.contentJson(getTos(meals, user.getCaloriesPerDay())));
//    }
}