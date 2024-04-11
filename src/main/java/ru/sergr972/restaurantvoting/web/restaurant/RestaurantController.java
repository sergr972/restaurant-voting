package ru.sergr972.restaurantvoting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.service.RestaurantService;

import java.time.Clock;
import java.util.List;

import static java.time.LocalDate.now;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    private final Clock clock;
    private final RestaurantService service;

    @GetMapping("/menus-today")
    @Operation(description = "Get all restaurants with menu for today")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> getAllWithMenuForToday() {
        log.info("Get all restaurants with menu for today");
        return service.getAllWithMenusForToday(now(clock));
    }

    @GetMapping("/win")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant getWin() {
        return service.getWin(now(clock));
    }
}