package ru.sergr972.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/profile";

    private final VoteRepository repository;

    @Autowired
    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users/votes")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesByDate() {
        log.info("get all Vote for ALL Users");
        return repository.findAllVotesByToDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @GetMapping("/users/{userId}/votes")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesForUser(@PathVariable int userId) {
        log.info("get all Vote for User {}", userId);
        return repository.findAllVotesUser(userId);
    }

    @GetMapping("/restaurant/{restaurantId}/votes")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesForRestaurant(@PathVariable int restaurantId) {
        log.info("get all Vote for Restaurant {}", restaurantId);
        return repository.findAllVotesRestaurant(restaurantId);
    }

    @GetMapping("/restaurant/votes")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesForALLRestaurantByToDay() {
        log.info("get all Vote for ALL Restaurant");
        return repository.findAllVotesForAllRestaurantByToDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}