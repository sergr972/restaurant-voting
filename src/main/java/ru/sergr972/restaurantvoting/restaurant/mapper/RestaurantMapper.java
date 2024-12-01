package ru.sergr972.restaurantvoting.restaurant.mapper;

import org.mapstruct.Mapper;
import ru.sergr972.restaurantvoting.restaurant.model.Restaurant;
import ru.sergr972.restaurantvoting.restaurant.to.RestaurantTo;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantTo toTo(Restaurant restaurant);
}