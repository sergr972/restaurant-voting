package ru.sergr972.restaurantvoting.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.mapper.VoteMapper;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static ru.sergr972.restaurantvoting.util.TimeUtil.checkTime;
import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteMapper voteMapper;

    @Autowired
    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
    }

    @GetMapping()
    @Operation(description = "Get votes history for user.")
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getAllVotesForUser(@AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        log.info("get all Vote for User {}", user);
        return voteRepository.getAllVotesByUser(user)
                .stream()
                .map(voteMapper::toTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/last-user-vote")
    @Operation(description = "Get user vote for today.")
    @ResponseStatus(HttpStatus.OK)
    public VoteTo getLastVoteForUser(@AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        log.info("get Vote for User {}", user);
        return voteMapper.toTo(voteRepository.getVoteByUserAndVoteDate(user, now()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Сreate a user voice.")
    public ResponseEntity<VoteTo> create(@RequestBody @Valid VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
            Vote newVote = new Vote(user, now(), restaurantRepository.getExisted(voteTo.getRestaurantId()));
            log.info("create {} for User {}", newVote, user);
            checkNew(newVote);
            voteRepository.save(newVote);
            VoteTo created = voteMapper.toTo(newVote);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping()
    @Operation(description = "Update a user voice.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        log.info("update {} for User {}", voteTo, user);
        Vote currentVotes = voteRepository.getVoteByUserAndVoteDate(user, now());
        checkTime();
        currentVotes.setRestaurant(restaurantRepository.getExisted(voteTo.getRestaurantId()));
        voteRepository.save(currentVotes);
    }
}
