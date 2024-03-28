package ru.sergr972.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteTo extends BaseTo{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate voteDate;

    private int restaurantId;

    public VoteTo(Integer id, LocalDate voteDate, int userId, int restaurantId) {
        super(id);
        this.userId = userId;
        this.voteDate = voteDate;
        this.restaurantId = restaurantId;
    }
}
