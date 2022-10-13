package ru.itmo.monsters.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reward")
public class RewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "shouldn't be null")
    @Size(message = "shouldn't be less than 0")
    @Column(name = "balloon_count")
    private int balloonCount;

    @NotNull(message = "shouldn't be null")
    @Size(message = "shouldn't be less than 0")
    @Column(name = "money", unique = true)
    private int money;

    @ManyToMany(mappedBy = "rewards")
    @ToString.Exclude
    private List<MonsterEntity> monsters;
}
