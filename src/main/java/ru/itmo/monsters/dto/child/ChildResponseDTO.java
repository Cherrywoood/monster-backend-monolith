package ru.itmo.monsters.dto.child;

import lombok.Data;
import ru.itmo.monsters.dto.DoorDTO;

import java.sql.Date;
import java.util.UUID;

@Data
public class ChildResponseDTO {
    private UUID id;
    private String name;
    private Date dob;
    private String gender;
    private DoorDTO door;
}
