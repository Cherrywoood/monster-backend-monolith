package ru.itmo.monsters.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class InfectedThingDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    @Size(min = 3, message = "shouldn't be less than 3")
    private String name;

    @NotNull(message = "shouldn't be null")
    private String door;
}
