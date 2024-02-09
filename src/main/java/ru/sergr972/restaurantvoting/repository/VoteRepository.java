package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT DISTINCT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.voteDate ASC ")
    List<Vote> findAllVotesUser(int userId);

    @Query("FROM Vote v WHERE v.voteDate=:date")
    Optional<List<Vote>> findAllVotesByToDay(LocalDate date);

    @Query("SELECT DISTINCT v FROM Vote v WHERE v.restaurant.id=:restaurantId ORDER BY v.voteDate ASC ")
    List<Vote> findAllVotesRestaurant(int restaurantId);

    @Query("FROM Vote v WHERE v.voteDate=:date")
    Optional<List<Vote>> findAllVotesForAllRestaurantByToDay(LocalDate date);
}
