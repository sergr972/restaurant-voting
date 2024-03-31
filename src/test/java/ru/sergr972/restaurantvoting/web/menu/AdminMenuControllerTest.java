package ru.sergr972.restaurantvoting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.repository.MenuRepository;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.web.menu.AdminMenuController.REST_URL;
import static ru.sergr972.restaurantvoting.web.menu.MenuTestData.*;
import static ru.sergr972.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;

class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MenuRepository repository;

//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getAllMenuItemsForRestaurant() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurants/{restaurantId}/all"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MENU_ITEM_MATCHER.contentJson(M1, M2, M3));
//    }

//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getMenuItemsForRestaurantByToday() {
//        perform(MockMvcRequestBuilders.get(REST_URL))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MENU_ITEM_MATCHER.contentJson(M2_TO, M3_TO));
//    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getMenuItemById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(M1_TO));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteMenuItemById() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(DISH_ID).isPresent());
    }

//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void createMenuItem() throws Exception {
//        Menu newMenu = getNewDish();
//        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newMenu)))
//                .andExpect(status().isCreated());
//
//        Menu created = MENU_ITEM_MATCHER.readFromJson(action);
//
//        int newId = created.id();
//        newMenu.setId(newId);
//        MENU_ITEM_MATCHER.assertMatch(created, newMenu);
//        MENU_ITEM_MATCHER.assertMatch(repository.getExisted(newId), newMenu);
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void updateMenuItem() throws Exception {
//        Menu updated = getUpdatedDish();
//        updated.setId(null);
//        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        MENU_ITEM_MATCHER.assertMatch(repository.getExisted(DISH_ID), getUpdatedDish());
//    }
}