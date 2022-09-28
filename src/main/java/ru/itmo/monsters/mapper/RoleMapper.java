package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.model.RoleEntity;

@RequiredArgsConstructor
@Component
public class RoleMapper {
    private final ModelMapper modelMapper;

    public RoleDTO mapEntityToDto(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDTO.class);
    }

    public RoleEntity mapDtoToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }
}
