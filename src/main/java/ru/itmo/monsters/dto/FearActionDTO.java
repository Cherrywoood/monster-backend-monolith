package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.monsters.dto.monster.MonsterDTO;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class FearActionDTO {

    @NotNull(message = "shouldn't be empty")
    private UUID id;

    @NotNull(message = "shouldn't be null")
    private MonsterDTO monsterDTO;

    @NotNull(message = "shouldn't be null")
    private DoorDTO doorDTO;

    @NotNull(message = "shouldn't be null")
    private Date date;

}
