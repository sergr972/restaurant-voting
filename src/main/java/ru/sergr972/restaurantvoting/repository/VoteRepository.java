package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT DISTINCT v FROM Vote v " +
            "JOIN FETCH v.user " +
            "JOIN FETCH v.restaurant r " +
            "JOIN FETCH r.menu m " +
            "WHERE m.date = v.voteDate AND v.user.id=:userId " +
            "ORDER BY v.voteDate ASC ")
    Optional<List<Vote>> findAllVotesByUser(int userId);

    @Query("SELECT v FROM Vote v " +
            "JOIN FETCH v.user " +
            "JOIN FETCH v.restaurant r " +
            "JOIN FETCH r.menu m " +
            "WHERE v.voteDate = :date AND m.date=:date " +
            "ORDER BY r.id ASC ")
    Optional<List<Vote>> findAllVotesByToDay(LocalDate date);

    Optional<Vote> getVoteByUserAndVoteDate(User user, LocalDate voteDate);
}
