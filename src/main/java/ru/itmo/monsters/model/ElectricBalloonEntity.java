package ru.itmo.monsters.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "electric_balloon")
public class ElectricBalloonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fear_action_id")
    private FearActionEntity fearActionEntity;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;

}
