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

    static final String REST_URL = "/api/votes";

    private final VoteRepository repository;

    @Autowired
    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesForUser(@PathVariable int userId) {
        log.info("get all Vote for User {}", userId);
        return repository.findAllVotesByUser(userId)
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllVotesByDate() {
        log.info("get all Vote for ALL Users");
        return repository.findAllVotesByToDay(LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}