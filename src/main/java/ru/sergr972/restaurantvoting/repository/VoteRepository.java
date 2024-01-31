package ru.sergr972.restaurantvoting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Vote;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
}
