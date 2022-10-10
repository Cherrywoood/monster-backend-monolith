package ru.itmo.monsters.dto.monster;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.monsters.dto.UserDTO;
import ru.itmo.monsters.enums.Gender;
import ru.itmo.monsters.enums.Job;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class MonsterDTO {

    private UUID id;

    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 4, max = 16, message = "must be between 4 and 16 characters")
    private String login;

    @NotNull(message = "shouldn't be null")
    private Job job;

    @NotBlank(message = "shouldn't be empty")
    @Size(max = 16, message = "shouldn't exceed 16 characters")
    private String name;

    @NotNull(message = "shouldn't be null")
    private Date dateOfBirth;

    @NotNull(message = "shouldn't be null")
    private Gender gender;

    @NotBlank(message = "shouldn't be null")
    @Size(max = 30, message = "shouldn't exceed 30 characters")
    @Email
    private String email;

    @NotNull(message = "shouldn't be null")
    @Size(message = "shouldn't be less than 0")
    private int salary;

}
