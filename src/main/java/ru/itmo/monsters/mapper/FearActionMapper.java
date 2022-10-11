package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.service.DoorService;
import ru.itmo.monsters.service.ElectricBalloonService;
import ru.itmo.monsters.service.MonsterService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class FearActionMapper {

    private final MonsterService monsterService;
    private final DoorService doorService;
    private final ElectricBalloonService electricBalloonService;

    public FearActionDTO mapEntityToDto(FearActionEntity fearActionEntity) {
        return FearActionDTO.builder()
                .id(fearActionEntity.getId())
                .monsterId(fearActionEntity.getMonsterEntity().getId())
                .doorId(fearActionEntity.getDoorEntity().getId())
                .date(fearActionEntity.getDate())
                .balloonsIds(fearActionEntity.getBalloons().stream().map(ElectricBalloonEntity::getId).collect(Collectors.toList()))
                .build();
    }

    public FearActionEntity mapDtoToEntity(FearActionDTO fearActionDTO) {
        return FearActionEntity.builder()
                .id(fearActionDTO.getId())
                .monsterEntity(monsterService.findById(fearActionDTO.getMonsterId()))
                .doorEntity(doorService.findById(fearActionDTO.getDoorId()))
                .date(fearActionDTO.getDate())
                .balloons(fearActionDTO.getBalloonsIds().stream().map(electricBalloonService::findById).collect(Collectors.toList()))
                .build();
    }

}
