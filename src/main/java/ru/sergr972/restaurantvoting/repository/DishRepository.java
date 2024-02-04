package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Dish;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT  DISTINCT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.name")
    List<Dish> findAllForRestaurant(int restaurantId);
}
