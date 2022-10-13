package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class FearActionDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    private UUID monsterId;

    @NotNull(message = "shouldn't be null")
    private UUID doorId;

    @NotNull(message = "shouldn't be null")
    private Date date;

    @NotNull(message = "can be empty, but shouldn't be null")
    private List<UUID> balloonsIds;

}
