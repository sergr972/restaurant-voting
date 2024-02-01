package ru.sergr972.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController {

    static final String REST_URL = "api/restaurant";

    @Autowired
    protected RestaurantRepository repository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant get(@PathVariable int id) {
        return repository.getRestaurantById(id);
    }
}