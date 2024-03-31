package ru.sergr972.restaurantvoting.web.restaurant;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.mapper.RestaurantMapper;
import ru.sergr972.restaurantvoting.mapper.RestaurantMapperImpl;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.to.RestaurantTo;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.sergr972.restaurantvoting.web.RestValidation.assureIdConsistent;
import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public AdminRestaurantController(RestaurantRepository repository, RestaurantMapperImpl restaurantMapper) {
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantTo> getAll() {
        return repository.findAll()
                .stream()
                .map(restaurantMapper::toTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantTo get(@PathVariable int restaurantId) {
        log.info("get {}", restaurantId);
        return restaurantMapper.toTo(repository.getExisted(restaurantId));
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("delete {}", restaurantId);
        repository.deleteExisted(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        RestaurantTo created = restaurantMapper.toTo(repository.save(restaurant));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        log.info("update restaurant with id={}", restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        repository.save(restaurant);
    }
}