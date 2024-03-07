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
import ru.sergr972.restaurantvoting.error.VoteException;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

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

    @GetMapping("/users/{userId}")
    @Operation(description = "Get votes history for user.")
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getAllVotesForUser(@PathVariable int userId) {
        log.info("get all Vote for User {}", userId);
        return voteRepository.findAllVotesByUser(userId)
                .orElseThrow(() -> new NotFoundException("not found"))
                .stream()
                .map(v -> new VoteTo(v.id(), v.getVoteDate(), v.getUser().getId(), v.getRestaurant().getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    @Operation(description = "Get all votes for today.")
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getAllVotesByDate() {
        log.info("get all Vote for ALL Users");
        return voteRepository.findAllVotesByToDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"))
                .stream()
                .map(v -> new VoteTo(v.id(), v.getVoteDate(), v.getUser().getId(), v.getRestaurant().getId()))
                .collect(Collectors.toList());
    }

    @PostMapping("/restaurants/{restaurantId}")
    @Operation(description = "Сreate a user voice.")
    public ResponseEntity<VoteTo> create(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {

        Optional<Vote> userVote = voteRepository.getVoteByUserAndVoteDate(authUser.getUser(), LocalDate.now());

        if (userVote.isEmpty()) {
            getRestaurant(restaurantId);
            Vote newVote = new Vote(authUser.getUser(), LocalDate.now(), restaurantRepository.getExisted(restaurantId));
            log.info("create {}", newVote);
            checkNew(newVote);
            voteRepository.save(newVote);
            VoteTo created = new VoteTo(newVote.id(), newVote.getVoteDate(), newVote.getUser().getId(), newVote.getRestaurant().getId());
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        throw new VoteException("user " + authUser.getUser().id() + " has voted");
    }

    @PutMapping("/restaurants/{restaurantId}")
    @Operation(description = "Update a user voice.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        LocalTime localTime = LocalDateTime.now().toLocalTime();
        LocalTime endOfVote = LocalTime.of(15, 0);

        getRestaurant(restaurantId);
        Optional<Vote> currentVotes = voteRepository.getVoteByUserAndVoteDate(authUser.getUser(), localDate);
        if (currentVotes.isEmpty()) {
            throw new VoteException("user " + authUser.getUser().id() + " not voted");
        } else {
            if (localTime.isBefore(endOfVote)) {
                currentVotes.get().setRestaurant(restaurantRepository.getExisted(restaurantId));
                voteRepository.save(currentVotes.get());
            } else {
                throw new VoteException("Re-voting time ended at 11:00 AM");
            }
        }
    }

    private void getRestaurant(int restaurantId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("restaurant " + restaurantId + " not found"));
    }
}
