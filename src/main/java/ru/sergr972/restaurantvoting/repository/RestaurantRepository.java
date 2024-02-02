package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.model.Restaurant;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r WHERE r.name = LOWER(:name)")
    Optional<Restaurant> findByNameIgnoreCase(String name);

    @Transactional
    default Restaurant prepareAndSave(Restaurant restaurant) {
        restaurant.setName(restaurant.getName());
        return save(restaurant);
    }

    default Restaurant getExistedByName(String name) {
        return findByNameIgnoreCase(name).orElseThrow(
                () -> new NotFoundException("Restaurant with name=" + name + " not found"));
    }
}
