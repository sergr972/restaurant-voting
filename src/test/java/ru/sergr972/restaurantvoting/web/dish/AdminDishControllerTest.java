package ru.sergr972.restaurantvoting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.model.Dish;
import ru.sergr972.restaurantvoting.repository.DishRepository;
import ru.sergr972.restaurantvoting.util.JsonUtil;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.web.dish.DishTestData.*;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    public static final String REST_URL_MENU = "/api/admin/restaurant/1/menu";
    private static final String REST_URL_MENU_SLASH = REST_URL_MENU + '/';

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(d1, d2, d3));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getDishMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENU_SLASH + DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(d1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteDishFromMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_MENU_SLASH + DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(DISH_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDish() throws Exception {
        Dish newDish = getNewDish();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);

        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDish() throws Exception {
        Dish updated = getUpdatedDish();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL_MENU_SLASH + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getExisted(DISH_ID), getUpdatedDish());
    }
}