package ru.itmo.monsters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
    private UUID id;

    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 4, max = 16, message = "must be between 4 and 16 characters")
    private String name;
}
