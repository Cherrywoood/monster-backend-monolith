package ru.itmo.monsters.model;

import ru.itmo.monsters.model.InfectedThing;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "infection")
public class Infection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /*
    //TODO: доделать, когда появится MonsterEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monster_id", referencedColumnName = "id", nullable = false)
    private MonsterEntity monster;
    */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "infected_thing_id", referencedColumnName = "id", nullable = false)
    private InfectedThing infectedThing;

    @Column(name = "infection_date", nullable = false)
    private Date infectionDate;

    @Column(name = "cure_date")
    private Date cureDate;
}
