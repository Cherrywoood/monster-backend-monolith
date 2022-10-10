package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CityDTO {

    private UUID id;

    @NotBlank(message = "shouldn't be empty")
    @Size(max = 20, message = "shouldn't exceed 20 characters")
    private String name;

}
