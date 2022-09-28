package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.conroller.exception.NotFoundException;
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
        return userRepository.save(mapper.mapDtoToEntity(userDTO, roleEntity));
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new NotFoundException("user not found by login " + login)
        );
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("user not found by id " + id)
        );
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findAllByRole(String role) {
        return roleService.findByName(role).getUsers();
    }

    public UserEntity updateRoleByLogin(Map<String, String> roleUpdate, String login) {
        RoleEntity roleEntity = roleService.findByName(roleUpdate.get("role"));
        UserEntity userEntity = userRepository.findByLogin(login).orElseThrow(
                () -> new NotFoundException("user not found by login " + login)
        );
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
        return userEntity;
    }

    public void deleteByLogin(String login) {
        userRepository.delete(
                userRepository
                        .findByLogin(login)
                        .orElseThrow(
                                () -> new NotFoundException("user not found by login " + login)
                        )
        );
    }

    //TODO: обработку ошибок сделать
}
