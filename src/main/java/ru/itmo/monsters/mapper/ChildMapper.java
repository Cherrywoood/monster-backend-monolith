package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.repository.DoorRepository;
import ru.itmo.monsters.service.DoorService;

@RequiredArgsConstructor
@Component
public class ChildMapper {

    private final DoorService doorService;
    private final ModelMapper modelMapper;

    public ChildDTO mapEntityToDto(ChildEntity childEntity) {
        return ChildDTO.builder()
                .id(childEntity.getId())
                .name(childEntity.getName())
                .dob(childEntity.getDob())
                .gender(childEntity.getGender().toString())
                .doorId(childEntity.getDoor().getId())
                .build();
    }

//    public ChildEntity mapDtoToEntity(ChildDTO childDTO) {
//        return ChildEntity.builder()
//                .name(childDTO.getName())
//                .dob(childDTO.getDob())
//                .gender(childDTO.getGender())
//                .door(doorService.findById(childDTO.getDoorId()))
//                .build();
//    }

    public ChildEntity mapDtoToEntity(ChildDTO childDTO, DoorEntity doorEntity) {
        ChildEntity childEntity = modelMapper.map(childDTO, ChildEntity.class);
        childEntity.setDoor(doorEntity);
        return childEntity;
    }
}
