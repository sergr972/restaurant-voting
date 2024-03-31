package ru.sergr972.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.mapper.MenuMapper;
import ru.sergr972.restaurantvoting.model.Menu;
import ru.sergr972.restaurantvoting.repository.MenuRepository;
import ru.sergr972.restaurantvoting.to.MenuTo;

import java.net.URI;
import java.util.List;

import static java.time.LocalDate.now;
import static ru.sergr972.restaurantvoting.web.RestValidation.assureIdConsistent;
import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {

    static final String REST_URL = "/api/admin/menus";

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Autowired
    public AdminMenuController(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @GetMapping("/restaurants/{restaurantId}/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Menu> getAllMenuItemsForRestaurant(@PathVariable int restaurantId) {
        log.info("get all Menu for restaurant {}", restaurantId);
        return menuRepository.findMenuItemsByRestaurant_Id(restaurantId);
    }

    @GetMapping("/restaurants/{restaurantId}/today")
    @ResponseStatus(HttpStatus.OK)
    public List<Menu> getMenuItemsForRestaurantByToday(@PathVariable int restaurantId) {
        log.info("get all MenuItems for restaurant {} by today", restaurantId);
        return menuRepository.findMenuItemsByRestaurant_IdAndDate(restaurantId, now());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuTo getMenuItemById(@PathVariable Integer id) {
        log.info("get MenuItem with id={}", id);
        return menuMapper.toTo(menuRepository.getExisted(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItemById(@PathVariable int id) {
        log.info("delete MenuItem with id={}", id);
        menuRepository.deleteExisted(id);
    }

    @PostMapping()
    public ResponseEntity<MenuTo> createMenuItem(@Valid @RequestBody MenuTo menuTo) {
        Menu menu = menuMapper.toMenu(menuTo);
        log.info("create Menu Item {} in Restaurant {}", menu, menu.getRestaurant().id());
        checkNew(menu);
        MenuTo created = menuMapper.toTo( menuRepository.save(menu));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenuItem(@Valid @RequestBody Menu menu, @PathVariable int id) {
        assureIdConsistent(menu, id);
        log.info("update Menu Item {} with id={} restaurant {}", menu, id, menu.getRestaurant().id());
        menuRepository.save(menu);
    }
}
