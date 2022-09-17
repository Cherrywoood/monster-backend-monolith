package ru.itmo.monsters.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.model.RoleEntity;
@RequiredArgsConstructor
@Component
public class RoleConverter {
    private final ModelMapper modelMapper;

    public RoleDTO convertEntityToDto(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDTO.class);
    }

    public RoleEntity convertDtoToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }
}
