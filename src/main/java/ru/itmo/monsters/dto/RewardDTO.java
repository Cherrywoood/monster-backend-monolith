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
public class RewardDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    @Size(message = "shouldn't be less than 0")
    private int balloonCount;

    @NotNull(message = "shouldn't be null")
    @Size(message = "shouldn't be less than 0")
    private int money;

}
