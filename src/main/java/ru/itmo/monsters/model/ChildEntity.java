package ru.itmo.monsters.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "child")
public class ChildEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "door_id", referencedColumnName = "id", nullable = false)
    private DoorEntity door;
}
