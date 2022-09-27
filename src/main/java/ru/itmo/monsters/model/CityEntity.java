package ru.itmo.monsters.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "city")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "shouldn't be empty")
    @Size(max = 20, message = "shouldn't exceed 20 characters")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "cityEntity")
    private List<ElectricBalloonEntity> balloons;
}
