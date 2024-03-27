package ru.sergr972.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.to.RestaurantTo;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantTo toTo(Restaurant restaurant);
}