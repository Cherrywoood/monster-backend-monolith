package ru.itmo.monsters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;

    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 4, max = 16, message = "must be between 4 and 16 characters")
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 5, message = "must be more than 5 characters")
    private String password;

    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 4, max = 16, message = "must be between 4 and 16 characters")
    private String role;
}
