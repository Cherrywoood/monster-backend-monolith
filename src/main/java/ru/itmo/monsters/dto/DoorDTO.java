package ru.itmo.monsters.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DoorDTO {

    private UUID id;
    private boolean is_active = false;
}
