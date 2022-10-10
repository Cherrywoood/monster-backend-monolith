package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.UserRequestDTO;
import ru.itmo.monsters.dto.UserResponseDTO;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserResponseDTO mapEntityToDto(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
        userResponseDTO.setRole(userEntity.getRole().getName());
        return userResponseDTO;
    }

    public UserEntity mapDtoToEntity(UserRequestDTO userRequestDTO, RoleEntity roleEntity) {
        UserEntity userEntity = modelMapper.map(userRequestDTO, UserEntity.class);
        userEntity.setRole(roleEntity);
        return userEntity;
    }


}
