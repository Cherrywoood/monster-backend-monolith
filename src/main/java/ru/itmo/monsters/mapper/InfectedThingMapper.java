package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.InfectedThingDTO;
import ru.itmo.monsters.model.InfectedThingEntity;
import ru.itmo.monsters.service.DoorService;

@RequiredArgsConstructor
@Component
public class InfectedThingMapper {

    private final DoorService doorService;

    public InfectedThingDTO mapEntityToDto(InfectedThingEntity infectedThingEntity) {
        return InfectedThingDTO.builder()
                .id(infectedThingEntity.getId())
                .name(infectedThingEntity.getName())
                .doorId(infectedThingEntity.getDoor().getId())
                .build();
    }

    public InfectedThingEntity mapDtoToEntity(InfectedThingDTO infectedThingDTO) {
        return InfectedThingEntity.builder()
                .id(infectedThingDTO.getId())
                .name(infectedThingDTO.getName())
                .door(doorService.findById(infectedThingDTO.getDoorId()))
                .build();
    }
}
