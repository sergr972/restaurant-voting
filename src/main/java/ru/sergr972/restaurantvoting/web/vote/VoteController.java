package ru.sergr972.restaurantvoting.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.service.VoteService;
import ru.sergr972.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteRepository voteRepository, VoteService voteService) {
        this.voteRepository = voteRepository;
        this.voteService = voteService;
    }

    @GetMapping("/users/{userId}")
    @Operation(description = "Get votes history for user.")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesForUser(@PathVariable int userId) {
        log.info("get all Vote for User {}", userId);
        return voteRepository.findAllVotesByUser(userId)
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @GetMapping("/users")
    @Operation(description = "Get all votes for today.")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesByDate() {
        log.info("get all Vote for ALL Users");
        return voteRepository.findAllVotesByToDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @PostMapping("/restaurants/{restaurantId}")
    @Operation(description = "Сreate or update a user voice.")
    public ResponseEntity<Vote> create(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {

        Vote created = voteService.createOrUpdate(restaurantId, authUser.getUser());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "restaurants")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}