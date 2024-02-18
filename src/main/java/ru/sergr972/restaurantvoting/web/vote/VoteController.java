package ru.sergr972.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.sergr972.restaurantvoting.web.RestValidation.assureIdConsistent;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesForUser(@PathVariable int userId) {
        log.info("get all Vote for User {}", userId);
        return voteRepository.findAllVotesByUser(userId)
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesByDate() {
        log.info("get all Vote for ALL Users");
        return voteRepository.findAllVotesByToDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<Vote> create(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create or update UserVote {}", authUser);
        LocalDate localDate = LocalDate.now();

        int userId = authUser.id();
        User user = authUser.getUser();
        assureIdConsistent(user, userId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        Optional<Vote> currentVoteByUsers = voteRepository.getVoteByUserAndVoteDate(user, localDate);
        Vote newVote;

        if (currentVoteByUsers.isEmpty()) {
            newVote = new Vote(user, localDate, restaurant);
        } else {
            newVote = currentVoteByUsers.get();
            newVote.setRestaurant(restaurant);
            newVote.setVoteDate(LocalDate.now());
        }

        Vote created = voteRepository.save(newVote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}