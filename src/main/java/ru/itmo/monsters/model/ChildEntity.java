package ru.itmo.monsters.model;

import lombok.*;
import ru.itmo.monsters.enums.Gender;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "child")
public class ChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private Date dob;

    @Column(name = "gender", nullable = false)
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "door_id", referencedColumnName = "id", nullable = false)
    private DoorEntity door;
}
