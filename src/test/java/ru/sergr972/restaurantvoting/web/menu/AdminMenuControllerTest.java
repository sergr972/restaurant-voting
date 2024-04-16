package ru.sergr972.restaurantvoting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sergr972.restaurantvoting.mapper.MenuMapper;
import ru.sergr972.restaurantvoting.repository.MenuRepository;
import ru.sergr972.restaurantvoting.to.MenuTo;
import ru.sergr972.restaurantvoting.util.JsonUtil;
import ru.sergr972.restaurantvoting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sergr972.restaurantvoting.web.menu.AdminMenuController.REST_URL;
import static ru.sergr972.restaurantvoting.web.data.MenuTestData.*;
import static ru.sergr972.restaurantvoting.web.data.UserTestData.ADMIN_MAIL;

class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';


    private final MenuRepository repository;
    private final MenuMapper menuMapper;

    @Autowired
    AdminMenuControllerTest(MenuRepository repository, MenuMapper menuMapper) {
        this.repository = repository;
        this.menuMapper = menuMapper;
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurants/1/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(M1_TO, M2_TO, M3_TO));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getForRestaurantByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurants/1/by-date"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(M2_TO, M3_TO
                ));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISH_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(M1_TO));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteById() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + DISH_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(DISH_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        MenuTo newMenu = getNewDish();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isCreated());

        MenuTo created = MENU_TO_MATCHER.readFromJson(action);

        int newId = created.id();
        newMenu.setId(newId);
        MENU_TO_MATCHER.assertMatch(created, newMenu);
        MENU_TO_MATCHER.assertMatch(menuMapper.toTo(repository.getExisted(newId)), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuTo updated = getUpdatedDish();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER.assertMatch(menuMapper.toTo(repository.getExisted(DISH_ID)), getUpdatedDish());
    }
}