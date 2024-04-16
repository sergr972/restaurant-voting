package ru.sergr972.restaurantvoting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.service.RestaurantService;
import ru.sergr972.restaurantvoting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    private final RestaurantService service;

    @GetMapping("/menu-date")
    @Operation(description = "Get all restaurants with menu for date. Default date - today.")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> getAllWithMenuForDate(@RequestParam @Nullable LocalDate date) {
        log.info("Get all restaurants with menu for date {}", date);
        return service.getAllWithMenuForDate(date);
    }

    @GetMapping("/{restaurantId}")
    @Operation(description = "Get restaurant by id")
    public RestaurantTo get(@PathVariable Integer restaurantId) {
        log.info("get restaurant {}", restaurantId);
        return service.get(restaurantId);
    }

    @GetMapping("/{restaurantId}/menu-date")
    @Operation(description = "Get restaurant with menu for date. Default date - today.")
    public Restaurant getWithMenu(@PathVariable Integer restaurantId, @DateTimeFormat(iso = DATE) @RequestParam @Nullable LocalDate date) {
        log.info("get restaurant {} with menu", restaurantId);
        return service.getByIdWithMenuForDate(restaurantId, date);
    }
}