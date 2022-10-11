package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.service.DoorService;
import ru.itmo.monsters.service.ElectricBalloonService;
import ru.itmo.monsters.service.MonsterService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .balloonsIds(getIdsFromListOfEntities(fearActionEntity.getBalloons()))
                .build();
    }

    public FearActionEntity mapDtoToEntity(FearActionDTO fearActionDTO) {
        return FearActionEntity.builder()
                .id(fearActionDTO.getId())
                .monsterEntity(monsterService.findById(fearActionDTO.getMonsterId()))
                .doorEntity(doorService.findById(fearActionDTO.getDoorId()))
                .date(fearActionDTO.getDate())
                .balloons(getListOfEntitiesFromIds(fearActionDTO.getBalloonsIds()))
                .build();
    }

    private List<UUID> getIdsFromListOfEntities(List<ElectricBalloonEntity> list) {
        ArrayList<UUID> ids = new ArrayList<>();
        list.forEach(e -> ids.add(e.getId()));
        return ids;
    }

    private List<ElectricBalloonEntity> getListOfEntitiesFromIds(List<UUID> list) {
        ArrayList<ElectricBalloonEntity> entities = new ArrayList<>();
        list.forEach(id -> entities.add(electricBalloonService.findById(id)));
        return entities;
    }
}
