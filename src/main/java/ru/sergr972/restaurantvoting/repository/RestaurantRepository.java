package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Transactional
    default Restaurant prepareAndSave(Restaurant restaurant) {
        restaurant.setName(restaurant.getName());
        return save(restaurant);
    }

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu ORDER BY r.id ASC")
    Optional<List<Restaurant>> getAllRestaurantsWithDishes();

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu WHERE r.id=:id")
    Optional<Restaurant> getRestaurantWithDishesById(int id);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.menu d WHERE d.date =:date ORDER BY r.id ASC")
    Optional<List<Restaurant>> findAllRestaurantWithMenuDay(LocalDate date);

    @Query("SELECT r FROM Restaurant r  JOIN FETCH r.menu d WHERE r.id=:id AND d.date =:date")
    Optional<Restaurant> getWithMenuDay(int id, LocalDate date);
}
