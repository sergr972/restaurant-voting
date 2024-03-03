package ru.sergr972.restaurantvoting.to;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class VoteTo extends BaseTo{

    private final int userId;
    private final LocalDate voteDate;
    private final int restaurantId;

    public VoteTo(Integer id, LocalDate voteDate, int userId, int restaurantId) {
        super(id);
        this.userId = userId;
        this.voteDate = voteDate;
        this.restaurantId = restaurantId;
    }

}
