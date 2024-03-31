package ru.sergr972.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.User;
import ru.sergr972.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> getAllVotesByUser(User user);

    Vote getVoteByUserAndVoteDate(User user, LocalDate voteDate);
}
