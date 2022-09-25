package ru.itmo.monsters.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.monsters.dto.auth.RoleDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private RoleDTO role;
}
