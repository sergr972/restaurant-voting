package ru.sergr972.restaurantvoting.vote.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.common.BaseRepository;
import ru.sergr972.restaurantvoting.user.model.User;
import ru.sergr972.restaurantvoting.vote.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> getAllVotesByUser(User user);

    Optional<Vote> getVoteByUserAndVoteDate(User user, LocalDate voteDate);
}
