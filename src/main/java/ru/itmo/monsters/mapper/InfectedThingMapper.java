package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.InfectedThingDTO;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.model.InfectedThingEntity;

@RequiredArgsConstructor
@Component
public class InfectedThingMapper {

    public InfectedThingDTO mapEntityToDto(InfectedThingEntity infectedThingEntity) {
        return InfectedThingDTO.builder()
                .id(infectedThingEntity.getId())
                .name(infectedThingEntity.getName())
                .doorId(infectedThingEntity.getDoor().getId())
                .build();
    }

    public InfectedThingEntity mapDtoToEntity(InfectedThingDTO infectedThingDTO, DoorEntity doorEntity) {
        return InfectedThingEntity.builder()
                .id(infectedThingDTO.getId())
                .name(infectedThingDTO.getName())
                .door(doorEntity)
                .build();
    }
}
