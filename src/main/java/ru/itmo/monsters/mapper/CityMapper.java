package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.model.CityEntity;

@RequiredArgsConstructor
@Component
public class CityMapper {
    private final ModelMapper modelMapper;

    public CityDTO mapEntityToDto(CityEntity cityEntity) {
        return modelMapper.map(cityEntity, CityDTO.class);
    }

    public CityEntity mapDtoToEntity(CityDTO cityDTO) {
        return modelMapper.map(cityDTO, CityEntity.class);
    }
}
