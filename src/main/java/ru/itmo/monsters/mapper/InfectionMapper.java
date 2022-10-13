package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.InfectionDTO;
import ru.itmo.monsters.model.InfectionEntity;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.service.InfectedThingService;

@RequiredArgsConstructor
@Component
public class InfectionMapper {

    private final InfectedThingService infectedThingService;

    public InfectionDTO mapEntityToDto(InfectionEntity infectionEntity) {
        return InfectionDTO.builder()
                .id(infectionEntity.getId())
                .monsterId(infectionEntity.getMonster().getId())
                .infectedThingId(infectionEntity.getInfectedThing().getId())
                .infectionDate(infectionEntity.getInfectionDate())
                .cureDate(infectionEntity.getCureDate())
                .build();
    }

    public InfectionEntity mapDtoToEntity(InfectionDTO infectionDTO, MonsterEntity monsterEntity) {
        return InfectionEntity.builder()
                .id(infectionDTO.getId())
                .monster(monsterEntity)
                .infectedThing(infectedThingService.findById(infectionDTO.getInfectedThingId()))
                .infectionDate(infectionDTO.getInfectionDate())
                .cureDate(infectionDTO.getCureDate())
                .build();
    }

}
