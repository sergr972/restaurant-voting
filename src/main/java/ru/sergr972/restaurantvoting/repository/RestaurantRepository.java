package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Restaurant;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r " +
            "WHERE r.id=:id")
    default Restaurant getRestaurantById(int id) {
        return null;
    }
}
