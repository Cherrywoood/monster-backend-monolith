package ru.itmo.monsters.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.service.DoorService;
import ru.itmo.monsters.service.ElectricBalloonService;
import ru.itmo.monsters.service.MonsterService;

@Component
public class FearActionMapper {

    private final MonsterService monsterService;
    private final DoorService doorService;
    private final ElectricBalloonService electricBalloonService;

    @Autowired
    public FearActionMapper(@Lazy MonsterService monsterService, DoorService doorService, @Lazy ElectricBalloonService electricBalloonService) {
        this.monsterService = monsterService;
        this.doorService = doorService;
        this.electricBalloonService = electricBalloonService;
    }

    public FearActionDTO mapEntityToDto(FearActionEntity fearActionEntity) {
        return FearActionDTO.builder()
                .id(fearActionEntity.getId())
                .monsterId(fearActionEntity.getMonsterEntity().getId())
                .doorId(fearActionEntity.getDoorEntity().getId())
                .date(fearActionEntity.getDate())
                .balloonsIds(fearActionEntity.getBalloons().stream().map(ElectricBalloonEntity::getId).toList())
                .build();
    }

    public FearActionEntity mapDtoToEntity(FearActionDTO fearActionDTO) {
        return FearActionEntity.builder()
                .id(fearActionDTO.getId())
                .monsterEntity(monsterService.findById(fearActionDTO.getMonsterId()))
                .doorEntity(doorService.findById(fearActionDTO.getDoorId()))
                .date(fearActionDTO.getDate())
                .balloons(fearActionDTO.getBalloonsIds().stream().map(electricBalloonService::findById).toList())
                .build();
    }

}
