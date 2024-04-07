package ru.sergr972.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.service.MenuService;
import ru.sergr972.restaurantvoting.to.MenuTo;

import java.net.URI;
import java.util.List;

import static ru.sergr972.restaurantvoting.web.RestValidation.assureIdConsistent;
import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminMenuController {

    static final String REST_URL = "/api/admin/menus";

    private final MenuService menuService;

    @GetMapping("/restaurants/{restaurantId}/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuTo> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("get all Menu for restaurant {}", restaurantId);
        return menuService.getAll(restaurantId);
    }

    @GetMapping("/restaurants/{restaurantId}/today")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuTo> getForRestaurantByToday(@PathVariable int restaurantId) {
        log.info("get all MenuItems for restaurant {} by today", restaurantId);
        return menuService.getLast(restaurantId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuTo getById(@PathVariable Integer id) {
        log.info("get MenuItem with id={}", id);
        return menuService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        log.info("delete MenuItem with id={}", id);
        menuService.delete(id);
    }

    @PostMapping()
    public ResponseEntity<MenuTo> create(@Valid @RequestBody MenuTo menuTo) {
        log.info("create Menu_Item {} in Restaurant {}", menuTo, menuTo.getRestaurantId());
        checkNew(menuTo);
        MenuTo created = menuService.create(menuTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("update Menu Item {} with id={} restaurant {}", menuTo, id, menuTo.getRestaurantId());
        assureIdConsistent(menuTo, id);
        menuService.update(menuTo);
    }
}
