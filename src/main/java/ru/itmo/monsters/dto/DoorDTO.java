package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class DoorDTO {

    private UUID id;
    private Boolean isActive;
}
