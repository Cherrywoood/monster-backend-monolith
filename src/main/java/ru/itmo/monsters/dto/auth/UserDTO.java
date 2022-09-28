package ru.itmo.monsters.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.monsters.dto.auth.RoleDTO;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;
}
