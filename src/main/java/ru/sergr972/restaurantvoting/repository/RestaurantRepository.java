package ru.sergr972.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu d WHERE d.date =:date")
    List<Restaurant> getAllWithMenuDay(LocalDate date);

    @Query("""
            SELECT r FROM
            (SELECT v.restaurant as r, COUNT(v.user) AS total FROM Vote v
            WHERE v.voteDate =:date GROUP BY v.restaurant ORDER BY total Desc limit 1)
           """)
    Restaurant getTop(LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu d WHERE d.date =:date AND r.id =:id")
    Restaurant getByIdWithMenuDay(LocalDate date, Integer id);
}
