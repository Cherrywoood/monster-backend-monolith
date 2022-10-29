package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.DoorDTO;
import ru.itmo.monsters.model.DoorEntity;

@RequiredArgsConstructor
@Component
public class DoorMapper {

    public DoorDTO mapEntityToDto(DoorEntity doorEntity) {
        return DoorDTO.builder()
                .id(doorEntity.getId())
                .active(doorEntity.isActive())
                .build();
    }

    public DoorEntity mapDtoToEntity(DoorDTO doorDTO) {
        return DoorEntity.builder()
                .id(doorDTO.getId())
                .active(doorDTO.getActive())
                .build();
    }
}
