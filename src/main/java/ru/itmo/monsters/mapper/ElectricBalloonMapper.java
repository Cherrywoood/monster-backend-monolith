package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.model.ElectricBalloonEntity;

@RequiredArgsConstructor
@Component
public class ElectricBalloonMapper {
    private final ModelMapper modelMapper;

    public ElectricBalloonDTO mapEntityToDto(ElectricBalloonEntity electricBalloonEntity) {
        return modelMapper.map(electricBalloonEntity, ElectricBalloonDTO.class);
    }

    public ElectricBalloonEntity mapDtoToEntity(ElectricBalloonDTO electricBalloonDTO) {
        return modelMapper.map(electricBalloonDTO, ElectricBalloonEntity.class);
    }
}
