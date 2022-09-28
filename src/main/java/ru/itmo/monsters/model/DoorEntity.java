package ru.itmo.monsters.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "door")
public class DoorEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "status", nullable = false)
    private boolean isActive = false;

    @OneToMany(mappedBy = "doorEntity")
    private List<FearActionEntity> fearActions;
}
