package ru.sergr972.restaurantvoting.restaurant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.restaurant.model.Restaurant;
import ru.sergr972.restaurantvoting.restaurant.service.RestaurantService;
import ru.sergr972.restaurantvoting.restaurant.to.RestaurantTo;

import java.net.URI;
import java.util.List;

import static ru.sergr972.restaurantvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.sergr972.restaurantvoting.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantTo> getAll() {
        log.info("Get all restaurants");
        return service.getAll();
    }

    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantTo get(@PathVariable int restaurantId) {
        log.info("Get restaurant with id={}", restaurantId);
        return service.get(restaurantId);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("Delete restaurant with id={}", restaurantId);
        service.delete(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("Create {}", restaurant);
        checkNew(restaurant);
        RestaurantTo created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        log.info("Update restaurant {}", restaurant);
        assureIdConsistent(restaurant, restaurantId);
        service.update(restaurant);
    }
}