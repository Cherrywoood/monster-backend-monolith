package ru.itmo.monsters.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.model.dto.RoleDTO;
import ru.itmo.monsters.model.entity.RoleEntity;

@Component
public class RoleConverter {
    private final ModelMapper modelMapper;

    public RoleConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDTO convertEntityToDto(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDTO.class);
    }

    public RoleEntity convertDtoToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }
}
