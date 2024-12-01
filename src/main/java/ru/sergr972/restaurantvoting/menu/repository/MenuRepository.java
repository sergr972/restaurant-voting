package ru.sergr972.restaurantvoting.menu.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.common.BaseRepository;
import ru.sergr972.restaurantvoting.menu.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    List<Menu> findMenuItemsByRestaurantId(int restaurantId);

    List<Menu> findMenuItemsByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
