package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu d WHERE d.date =:date")
    List<Restaurant> getAllWithMenuDay(LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu d WHERE d.date =:date AND r.id=:id")
    Optional<Restaurant> getByIdWithMenuDay (Integer id, LocalDate date);
}
