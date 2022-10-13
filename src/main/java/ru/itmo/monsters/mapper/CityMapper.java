package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.model.CityEntity;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.service.ElectricBalloonService;
import ru.itmo.monsters.service.MonsterService;

import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Component
public class CityMapper {

    private final ElectricBalloonService electricBalloonService;

    @Autowired
    public CityMapper(@Lazy ElectricBalloonService electricBalloonService) {
        this.electricBalloonService = electricBalloonService;
    }

    public CityDTO mapEntityToDto(CityEntity cityEntity) {
        return CityDTO.builder()
                .id(cityEntity.getId())
                .name(cityEntity.getName())
                .balloonsIds(cityEntity.getBalloons().stream().map(ElectricBalloonEntity::getId).collect(Collectors.toList()))
                .build();
    }

    public CityEntity mapDtoToEntity(CityDTO cityDTO) {
        return CityEntity.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .balloons(cityDTO.getBalloonsIds().stream().map(electricBalloonService::findById).collect(Collectors.toList()))
                .build();
    }
}
