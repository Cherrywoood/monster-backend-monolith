package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.service.DoorService;

@RequiredArgsConstructor
@Component
public class ChildMapper {

    private final DoorService doorService;

    public ChildDTO mapEntityToDto(ChildEntity childEntity) {
        return ChildDTO.builder()
                .id(childEntity.getId())
                .name(childEntity.getName())
                .dob(childEntity.getDob())
                .gender(childEntity.getGender())
                .doorId(childEntity.getDoor().getId().toString())
                .build();
    }

    public ChildEntity mapDtoToEntity(ChildDTO childDTO) {
        return ChildEntity.builder()
                .id(childDTO.getId())
                .name(childDTO.getName())
                .dob(childDTO.getDob())
                .gender(childDTO.getGender())
                .door(doorService.findById(childDTO.getId()))
                .build();
    }
}
