package ru.itmo.monsters.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reward")
public class RewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Size(message = "Balloon count shouldn't be less than 0")
    @Column(name = "balloon_count")
    private int balloonCount;

    @NotNull
    @Size(message = "Amount of money shouldn't be less than 0")
    @Column(name = "money")
    private int money;

    @ManyToMany(mappedBy = "rewards")
    private List<MonsterEntity> monsters;
}
