package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.model.FearActionEntity;

@RequiredArgsConstructor
@Component
public class FearActionMapper {
    private final ModelMapper modelMapper;

    public FearActionDTO mapEntityToDto(FearActionEntity fearActionEntity) {
        return modelMapper.map(fearActionEntity, FearActionDTO.class);
    }

    public FearActionEntity mapDtoToEntity(FearActionDTO fearActionDTO) {
        return modelMapper.map(fearActionDTO, FearActionEntity.class);
    }
}
