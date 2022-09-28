package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.dto.UserDTO;
import ru.itmo.monsters.mapper.UserMapper;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final RoleService roleService;

    public UserEntity save(UserDTO userDTO) {
        System.out.println(userDTO);
        RoleEntity roleEntity = roleService.findByName(userDTO.getRole());
        UserEntity userEntity = userRepository.save(mapper.mapDtoToEntity(userDTO, roleEntity));
        System.out.println(userEntity);
        return userEntity;
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findAllByRole(String role) {
        return roleService.findByName(role).getUsers();
    }

    public UserEntity updateRoleByLogin(Map<String, String> roleUpdate, String login) {
        UserEntity userEntity = userRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
        RoleEntity roleEntity = roleService.findByName(roleUpdate.get("role"));
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
        return userEntity;
    }

    public void deleteByLogin(String login) {
        userRepository.delete(
                userRepository
                        .findByLogin(login)
                        .orElseThrow(IllegalArgumentException::new)
        );
    }

    //TODO: обработку ошибок сделать
}
