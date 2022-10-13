package ru.itmo.monsters.dto.monster;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonsterRatingDTO {

    @NotNull(message = "shouldn't be null")
    private UUID monsterID;

    @NotNull(message = "shouldn't be null")
    @Size(message = "shouldn't be less than 0")
    private int countBalloons;
}
