package ru.itmo.monsters.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "electric_balloon")
public class ElectricBalloonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fear_action_id")
    private FearActionEntity fearActionEntity;

    @NotNull(message = "shouldn't be empty")
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;

}
