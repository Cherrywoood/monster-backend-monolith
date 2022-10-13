package ru.itmo.monsters.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fear_action")
public class FearActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "shouldn't be null")
    @ManyToOne
    @JoinColumn(name = "monster_id")
    private MonsterEntity monsterEntity;

    @NotNull(message = "shouldn't be null")
    @ManyToOne
    @JoinColumn(name = "door_id")
    private DoorEntity doorEntity;

    @NotNull(message = "shouldn't be null")
    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "fearActionEntity")
    @ToString.Exclude
    private List<ElectricBalloonEntity> balloons;
}
