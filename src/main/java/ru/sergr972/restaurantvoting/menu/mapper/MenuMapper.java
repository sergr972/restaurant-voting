package ru.sergr972.restaurantvoting.menu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sergr972.restaurantvoting.menu.model.Menu;
import ru.sergr972.restaurantvoting.menu.to.MenuTo;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    MenuTo toTo(Menu menu);

    @Mapping(target = "restaurant.id", source = "restaurantId")
    Menu toMenu(MenuTo menuTo);
}