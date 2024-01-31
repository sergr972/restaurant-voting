package ru.sergr972.restaurantvoting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Dish;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
