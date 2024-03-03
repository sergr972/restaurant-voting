package ru.sergr972.restaurantvoting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.error.VoteException;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.repository.VoteRepository;
import ru.sergr972.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public VoteTo createOrUpdate(int restaurantId, User user) {
        log.info("create or update UserVote {}", user);
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        LocalTime localTime = LocalDateTime.now().toLocalTime();
        LocalTime endOfVote = LocalTime.of(11, 0);

        if (localTime.isBefore(endOfVote)) {
            Optional<Vote> currentVotes = voteRepository.getVoteByUserAndVoteDate(user, localDate);
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new NotFoundException("not found restaurant " + restaurantId));
            if (currentVotes.isEmpty()) {
                Vote newVote = new Vote(null, localDate, user, restaurant);
                voteRepository.save(newVote);
                return new VoteTo(null, localDate, newVote.getUser().getId(), newVote.getRestaurant().getId());
            } else {
                currentVotes.get().setRestaurant(restaurant);
                voteRepository.save(currentVotes.get());
                return new VoteTo(currentVotes.get().id(), currentVotes.get().getVoteDate(),
                        currentVotes.get().getUser().id(), currentVotes.get().getRestaurant().getId());
            }
        } else {
            throw new VoteException("Voting time ended at 11:00 AM");
        }
    }
}
