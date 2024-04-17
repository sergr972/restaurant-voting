package ru.sergr972.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.mapper.VoteMapper;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.to.VoteTo;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteService {

    private final Clock clock;
    private final VoteMapper mapper;
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public List<VoteTo> get(User user) {
        return get(voteRepository.getAllVotesByUser(user));
    }

    @Transactional(readOnly = true)
    public VoteTo getByDate(User user, LocalDate date) {
        LocalDate voteDate = date == null ? now(clock) : date;
        return mapper.toTo(getVote(user, voteDate));
    }

    @Transactional
    public VoteTo create(int restaurantId, User user) {
        Vote vote = new Vote(user, now(clock), restaurantRepository.getExisted(restaurantId));
        return mapper.toTo(voteRepository.save(vote));
    }

    @Transactional
    public void update(int restaurantId, User user) {
        Vote vote = getVote(user, now(clock));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        voteRepository.save(vote);
    }

    private Vote getVote(User user, LocalDate voteDate) {
        return voteRepository.getVoteByUserAndVoteDate(user, voteDate)
                .orElseThrow(() -> new NotFoundException("User " + user + " not voted"));
    }

    private List<VoteTo> get(List<Vote> voteList) {
        return voteList
                .stream()
                .map(mapper::toTo)
                .collect(Collectors.toList());
    }
}