package ru.itmo.monsters.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
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

    @Min(value = 0, message = "shouldn't be less than 0")
    @Column(name = "balloon_count")
    private int balloonCount;

    @Min(value = 0, message = "shouldn't be less than 0")
    @Column(name = "money", unique = true)
    private int money;

    @ManyToMany(mappedBy = "rewards")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<MonsterEntity> monsters;
}
