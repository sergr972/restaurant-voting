package ru.sergr972.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {

    static final String REST_URL = "/api/profile/restaurants";

    protected final RestaurantRepository repository;

    @Autowired
    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> getAllWithMenuDay() {
        return repository.findAllRestaurantWithMenuDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant getRestaurantWithMenuDay(@PathVariable int id) {
        log.info("get {}", id);
        return repository.getWithMenuDay(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}