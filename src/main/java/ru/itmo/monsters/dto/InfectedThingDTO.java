package ru.itmo.monsters.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InfectedThingDTO {

    private UUID id;
    private String name;
    private DoorDTO door;
}
