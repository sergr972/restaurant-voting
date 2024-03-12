package ru.sergr972.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.to.VoteTo;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    default VoteTo toTo(Vote vote) {
        VoteTo voteTo = new VoteTo();
        voteTo.setId(vote.getId());
        voteTo.setUserId(vote.getUser().getId());
        voteTo.setVoteDate(vote.getVoteDate());
        voteTo.setRestaurantId(vote.getRestaurant().getId());
        return voteTo;
    }
}