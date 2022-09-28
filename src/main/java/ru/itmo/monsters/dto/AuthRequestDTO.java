package ru.itmo.monsters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 4, max = 16, message = "must be between 4 and 16 characters")
    private String login;

    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 5, message = "must be more than 5 characters")
    private String password;
}
