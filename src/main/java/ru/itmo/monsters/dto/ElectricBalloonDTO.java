package ru.itmo.monsters.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ElectricBalloonDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    private UUID fearActionId;

    @NotBlank(message = "shouldn't be empty")
    @Size(max = 20, message = "shouldn't exceed 20 characters")
    private String cityName;
}
