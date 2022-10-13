package ru.itmo.monsters.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardDTO {

    private UUID id;

    @Min(value = 0, message = "shouldn't be less than 0")
    private int balloonCount;

    @Min(value = 0, message = "shouldn't be less than 0")
    private int money;

    @NotNull(message = "can be empty, but shouldn't be null")
    private List<UUID> monstersIds;

}
