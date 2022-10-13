package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.UserRequestDTO;
import ru.itmo.monsters.dto.UserResponseDTO;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.service.RoleService;

import java.util.Map;

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

    public UserEntity update(Map<String, String> updates, UserEntity userEntity, RoleService roleService) {
        String role = updates.get("role");
        String password = updates.get("password");
        String login = updates.get("login");

        if (role != null) {
            userEntity.setRole(
                    roleService.findByName(role)
            );
        }
        if (password != null) {
            userEntity.setPassword(password);
        }
        if (login != null) {
            userEntity.setLogin(login);
        }
        return userEntity;
    }
}
