package ru.sergr972.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sergr972.restaurantvoting.model.Vote;
import ru.sergr972.restaurantvoting.to.VoteTo;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "userId", source = "user.id")
    VoteTo toTo(Vote vote);
}