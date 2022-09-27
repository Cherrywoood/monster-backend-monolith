package ru.itmo.monsters.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fear_action")
public class FearActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "monster_id")
    private MonsterEntity monsterEntity;

    @ManyToOne
    @JoinColumn(name = "door_id")
    private DoorEntity doorEntity;

    @Column(name = "date")
    private Date date;
}
