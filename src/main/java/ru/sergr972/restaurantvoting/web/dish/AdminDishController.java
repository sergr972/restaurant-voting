package ru.sergr972.restaurantvoting.web.dish;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.model.Dish;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.repository.DishRepository;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;

import java.net.URI;
import java.util.List;

import static ru.sergr972.restaurantvoting.web.RestValidation.assureIdConsistent;
import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    protected DishRepository dishRepository;
    protected RestaurantRepository restaurantRepository;

    @Autowired
    public AdminDishController(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllDishesForRestaurant(@PathVariable int restaurantId) {
        log.info("get all Dish for restaurant {}", restaurantId);
        return dishRepository.findAllForRestaurant(restaurantId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dish getDishesById(@PathVariable int id, @PathVariable String restaurantId) {
        log.info("get Dish with id={} in menu for Restaurant with id={}", id, restaurantId);
        return dishRepository.getExisted(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishesById(@PathVariable int id, @PathVariable String restaurantId) {
        log.info("delete Dish with id={} for Restaurant id={}", id, restaurantId);
        dishRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishes(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create Dish {} in Restaurant id={}", dish, restaurantId);
        checkNew(dish);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        dish.setRestaurant(restaurant);
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDishes(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update Dish {} with id={} restaurant id={}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        dishRepository.save(dish);
    }
}
