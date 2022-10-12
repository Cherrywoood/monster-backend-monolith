package ru.itmo.monsters.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "infected_thing")
public class InfectedThingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "door_id", referencedColumnName = "id", nullable = false)
    private DoorEntity door;
}
