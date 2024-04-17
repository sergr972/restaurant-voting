package ru.sergr972.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.error.NotFoundException;
import ru.sergr972.restaurantvoting.mapper.RestaurantMapper;
import ru.sergr972.restaurantvoting.model.Restaurant;
import ru.sergr972.restaurantvoting.repository.RestaurantRepository;
import ru.sergr972.restaurantvoting.to.RestaurantTo;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final Clock clock;
    private final RestaurantMapper mapper;
    private final RestaurantRepository repository;

    @Transactional(readOnly = true)
    public List<Restaurant> getAllWithMenuForDate(LocalDate date) {
        LocalDate localDate = date == null ? now(clock) : date;
        return repository.getAllWithMenuDay(localDate);
    }

    @Transactional(readOnly = true)
    public Restaurant getByIdWithMenuForDate(Integer id, LocalDate date) {
        LocalDate localDate = date == null ? now(clock) : date;
        return repository.getByIdWithMenuDay(id, localDate)
                .orElseThrow(() -> new NotFoundException("Restaurant " + id + " not have menu for date " + date));
    }

    @Transactional(readOnly = true)
    public List<RestaurantTo> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toTo)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RestaurantTo get(int restaurantId) {
        return mapper.toTo(repository.getExisted(restaurantId));
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public void delete(int restaurantId) {
        repository.deleteExisted(restaurantId);
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public RestaurantTo create(Restaurant restaurant) {
        return mapper.toTo(repository.save(restaurant));
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public void update(Restaurant restaurant) {
        repository.save(restaurant);
    }
}