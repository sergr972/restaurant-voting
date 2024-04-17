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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class UserRestaurantController {

    static final String REST_URL = "/api/restaurants";

    private final RestaurantService service;

    @GetMapping
    @Operation(description = "Get all restaurants with menu for date or list without menu (default: with menu, date=today). Enter date in format yyyy-MM-dd. ")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> getAll(@RequestParam(required = false, defaultValue = "true") Boolean withMenu,
                                   @RequestParam @DateTimeFormat(iso = DATE) @Nullable LocalDate date) {
        return service.getAll(date, withMenu);
    }

    @GetMapping("/{restaurantId}/menus")
    @Operation(description = "Get restaurant with menu for date (default: date=today). Enter date in format yyyy-MM-dd.")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant getByIdWithMenuForDate(@PathVariable Integer restaurantId, @RequestParam @DateTimeFormat(iso = DATE) @Nullable LocalDate date) {
        return service.getByIdWithMenuForDate(restaurantId, date);
    }
}