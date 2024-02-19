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

    @Query("FROM Vote v WHERE v.user.id=:userId")
    Optional<List<Vote>> findAllVotesByUser(int userId);

    @Query("FROM Vote v WHERE v.voteDate = :date")
    Optional<List<Vote>> findAllVotesByToDay(LocalDate date);

    Optional<Vote> getVoteByUserAndVoteDate(User user, LocalDate voteDate);
}
