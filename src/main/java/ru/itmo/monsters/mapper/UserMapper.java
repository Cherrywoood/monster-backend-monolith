package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.auth.UserDTO;
import ru.itmo.monsters.model.auth.UserEntity;
@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDTO mapEntityToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserEntity mapDtoToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }


}
