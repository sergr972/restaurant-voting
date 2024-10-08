package ru.sergr972.restaurantvoting.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sergr972.restaurantvoting.service.VoteService;
import ru.sergr972.restaurantvoting.to.VoteTo;
import ru.sergr972.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static ru.sergr972.restaurantvoting.util.TimeUtil.checkTime;
import static ru.sergr972.restaurantvoting.web.RestValidation.checkNew;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class UserVoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteService service;

    @GetMapping(REST_URL)
    @Operation(description = "Get votes history for user.")
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all Vote for User {}", authUser.getUser());
        return service.get(authUser.getUser());
    }

    @GetMapping(REST_URL + "/by-date")
    @Operation(description = "Get users vote for date (default: date=today). Enter date in format yyyy-MM-dd.")
    @ResponseStatus(HttpStatus.OK)
    public VoteTo getByDate(@AuthenticationPrincipal AuthUser authUser, @RequestParam @DateTimeFormat(iso = DATE) @Nullable LocalDate date) {
        log.info("get Vote for User {} by date {}", authUser.getUser(), date);
        return service.getByDate(authUser.getUser(), date);
    }

    @PostMapping(REST_URL)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a user voice.")
    public ResponseEntity<VoteTo> create(@RequestBody @Valid VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create {} for User {}", voteTo, authUser.getUser());
        checkNew(voteTo);
        VoteTo created = service.create(voteTo.getRestaurantId(), authUser.getUser());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(REST_URL)
    @Operation(description = "Update a user voice.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} for User {}", voteTo, authUser.getUser());
        checkTime();
        service.update(voteTo.getRestaurantId(), authUser.getUser());
    }
}
