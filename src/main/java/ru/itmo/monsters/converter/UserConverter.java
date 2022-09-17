package ru.itmo.monsters.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.UserDTO;
import ru.itmo.monsters.model.UserEntity;
@RequiredArgsConstructor
@Component
public class UserConverter {
    private final ModelMapper modelMapper;

    public UserDTO convertEntityToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserEntity convertDtoToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }


}
