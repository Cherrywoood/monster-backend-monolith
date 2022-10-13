package ru.itmo.monsters.dto.monster;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.monsters.enums.Gender;
import ru.itmo.monsters.enums.Job;

import javax.validation.constraints.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class MonsterDTO {

    private UUID id;

    @NotNull(message = "shouldn't be null")
    private UUID userId;

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
    @Min(value = 0, message = "shouldn't be less than 0")
    private int salary;

    @NotNull(message = "can be empty, but shouldn't be null")
    private List<UUID> rewardsIds;

    @NotNull(message = "can be empty, but shouldn't be null")
    private List<UUID> fearActionsIds;

}
