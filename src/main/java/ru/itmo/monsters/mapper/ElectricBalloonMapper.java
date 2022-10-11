package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.service.CityService;
import ru.itmo.monsters.service.FearActionService;

@RequiredArgsConstructor
@Component
public class ElectricBalloonMapper {

    private final FearActionService fearActionService;
    private final CityService cityService;

    public ElectricBalloonDTO mapEntityToDto(ElectricBalloonEntity electricBalloonEntity) {
        return ElectricBalloonDTO.builder()
                .id(electricBalloonEntity.getId())
                .fearActionId(electricBalloonEntity.getFearActionEntity().getId())
                .cityName(electricBalloonEntity.getCityEntity().getName())
                .build();
    }

    public ElectricBalloonEntity mapDtoToEntity(ElectricBalloonDTO electricBalloonDTO) {
        return ElectricBalloonEntity.builder()
                .fearActionEntity(fearActionService.findById(electricBalloonDTO.getFearActionId()))
                .cityEntity(cityService.findByName(electricBalloonDTO.getCityName()))
                .id(electricBalloonDTO.getId())
                .build();
    }
}
