package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.model.CityEntity;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.service.ElectricBalloonService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CityMapper {

    private final ElectricBalloonService electricBalloonService;

    public CityDTO mapEntityToDto(CityEntity cityEntity) {
        return CityDTO.builder()
                .id(cityEntity.getId())
                .name(cityEntity.getName())
                .balloonsIds(getIdsFromListOfEntities(cityEntity.getBalloons()))
                .build();
    }

    public CityEntity mapDtoToEntity(CityDTO cityDTO) {
        return CityEntity.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .balloons(getListOfEntitiesFromIds(cityDTO.getBalloonsIds()))
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
