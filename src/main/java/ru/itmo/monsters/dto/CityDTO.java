package ru.itmo.monsters.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private UUID id;

    @NotBlank(message = "shouldn't be empty")
    @Size(max = 20, message = "shouldn't exceed 20 characters")
    private String name;

    @NotNull(message = "can be empty, but shouldn't be null")
    private List<UUID> balloonsIds;
}
