package ru.sergr972.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "users_votes_unique_date_idx"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vote extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

}
