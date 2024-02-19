package ru.sergr972.restaurantvoting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergr972.restaurantvoting.error.VoteException;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.repository.VoteRepository;

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

    public Vote createOrUpdate(int restaurantId, User user) {
        log.info("create or update UserVote {}", user);
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        LocalTime localTime = LocalDateTime.now().toLocalTime();
        LocalTime endOfVote = LocalTime.of(11, 0);

        if (localTime.isBefore(endOfVote)) {

            Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
            Optional<Vote> currentVoteByUsers = voteRepository.getVoteByUserAndVoteDate(user, localDate);
            Vote newVote;

            if (currentVoteByUsers.isEmpty()) {
                newVote = new Vote(user, localDate, restaurant);
            } else {
                newVote = currentVoteByUsers.get();
                newVote.setRestaurant(restaurant);
                newVote.setVoteDate(LocalDate.now());
            }
            return voteRepository.save(newVote);
        } else {
            throw new VoteException("Voting time ended at 11:00 AM");
        }
    }
}
