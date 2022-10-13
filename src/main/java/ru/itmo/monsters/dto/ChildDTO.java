package ru.itmo.monsters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ChildDTO {
    private UUID id;

    @NotNull(message = "shouldn't be null")
    @Size(min = 1, message = "shouldn't be less than 1")
    private String name;

    @NotNull(message = "shouldn't be null")
    private Date dob;

    @NotNull(message = "shouldn't be null")
    private String gender;

    @NotNull(message = "shouldn't be null")
    private UUID doorId;
}
