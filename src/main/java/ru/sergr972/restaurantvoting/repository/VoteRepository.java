package ru.sergr972.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> getAllVotesByUser(User user);

    Optional<Vote> getVoteByUserAndVoteDate(User user, LocalDate voteDate);

    List<Vote> findAllByVoteDate(LocalDate date);
}
