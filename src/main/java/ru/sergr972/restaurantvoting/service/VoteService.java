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
        return voteRepository.getAllVotesByUser(user)
                .stream()
                .map(mapper::toTo)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VoteTo getLast(User user) {
        return mapper.toTo(getVote(user));
    }

    @Transactional
    public VoteTo create(int restaurantId, User user) {
        Vote vote = new Vote(user, now(clock), restaurantRepository.getExisted(restaurantId));
        return mapper.toTo(voteRepository.save(vote));
    }

    @Transactional
    public void update(int restaurantId, User user) {
        Vote vote = getVote(user);
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        voteRepository.save(vote);
    }

    private Vote getVote(User user) {
        return voteRepository.getVoteByUserAndVoteDate(user, now(clock))
                .orElseThrow(() -> new NotFoundException("User " + user + " not voted"));
    }
}