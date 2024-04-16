package ru.sergr972.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergr972.restaurantvoting.mapper.MenuMapper;
import ru.sergr972.restaurantvoting.model.Menu;
import ru.sergr972.restaurantvoting.repository.MenuRepository;
import ru.sergr972.restaurantvoting.to.MenuTo;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {

    private final Clock clock;
    private final MenuMapper mapper;
    private final MenuRepository repository;

    @Transactional(readOnly = true)
    public List<MenuTo> getAll(int restaurantId) {
        return get(repository.findMenuItemsByRestaurantId(restaurantId));
    }

    @Transactional(readOnly = true)
    public List<MenuTo> getByDate(int restaurantId, LocalDate date) {
        LocalDate localDate = date == null ? now(clock) : date;
        return get(repository.findMenuItemsByRestaurantIdAndDate(restaurantId, localDate));
    }

    @Transactional(readOnly = true)
    public MenuTo getById(Integer id) {
        return mapper.toTo(repository.getExisted(id));
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public MenuTo create(MenuTo menuTo) {
        Menu menu = mapper.toMenu(menuTo);
        return mapper.toTo(repository.save(menu));
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public void update(MenuTo menuTo) {
        repository.save(mapper.toMenu(menuTo));
    }

    private List<MenuTo> get(List<Menu> menuList) {
        return menuList
                .stream()
                .map(mapper::toTo)
                .collect(Collectors.toList());
    }
}