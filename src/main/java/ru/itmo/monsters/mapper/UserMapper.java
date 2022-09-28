package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.UserDTO;
import ru.itmo.monsters.model.UserEntity;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDTO mapEntityToDto(UserEntity userEntity) {
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        userDTO.setRole(userEntity.getRole().getName());
        return userDTO;
    }

    public UserEntity mapDtoToEntity(UserDTO userDTO, RoleEntity roleEntity) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setRole(roleEntity);
        return userEntity;
    }


}
