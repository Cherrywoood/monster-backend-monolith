package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.InfectionDTO;
import ru.itmo.monsters.model.InfectionEntity;
import ru.itmo.monsters.service.InfectedThingService;

@RequiredArgsConstructor
@Component
public class InfectionMapper {

    //private final MonsterService monsterService;
    private final InfectedThingService infectedThingService;

    public InfectionDTO mapEntityToDto(InfectionEntity infectionEntity) {
        return InfectionDTO.builder()
                .id(infectionEntity.getId())
                .monsterId(infectionEntity.getMonster().getId())
                .infectedThing(infectionEntity.getInfectedThing().getId())
                .infectionDate(infectionEntity.getInfectionDate())
                .cureDate(infectionEntity.getCureDate())
                .build();
    }

//    public InfectionEntity mapDtoToEntity(InfectionDTO infectionDTO) {
//        return InfectionEntity.builder()
//                .id(infectionDTO.getId())
//                .monster(monsterService.findById(infectionDTO.getMonsterId()))
//                .infectedThing(infectedThingService.findById(infectionDTO.getInfectedThing()))
//                .infectionDate(infectionDTO.getInfectionDate())
//                .cureDate(infectionDTO.getCureDate())
//                .build();
//    }
}
