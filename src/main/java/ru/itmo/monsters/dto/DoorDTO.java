package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Getter
@Setter
@Builder
public class DoorDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    private boolean is_active = false;
}
