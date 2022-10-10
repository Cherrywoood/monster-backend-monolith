package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ElectricBalloonDTO {

    private UUID id;

    private FearActionDTO fearActionDTO;

    @NotNull(message = "shouldn't be null")
    private CityDTO cityDTO;
}
