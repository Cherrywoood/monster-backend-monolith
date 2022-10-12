package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InfectedThingDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    @Size(min = 3, message = "shouldn't be less than 3")
    private String name;

    @NotNull(message = "shouldn't be null")
    private UUID doorId;
}
