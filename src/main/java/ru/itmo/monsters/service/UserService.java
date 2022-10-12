package ru.itmo.monsters.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.UserDTO;
import ru.itmo.monsters.mapper.UserMapper;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.repository.UserRepository;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private static final String EXC_MES_LOGIN = "user not found by login";
    private static final String EXC_MES_ID = "user not found by id";
    private static final String EXC_EXIST = "user with this login exists";
    private final UserRepository userRepository;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public UserEntity save(UserDTO userDTO) { //регистрация
        if (userRepository.findByLogin(userDTO.getLogin()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST);
        }
        RoleEntity roleEntity = roleService.findByName(userDTO.getRole());
        UserEntity userEntity = mapper.mapDtoToEntity(userDTO, roleEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new NotFoundException(EXC_MES_LOGIN + " " + login)
        );
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + " " + id)
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
                () -> new NotFoundException(EXC_MES_LOGIN + " " + login)
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
                                () -> new NotFoundException(EXC_MES_LOGIN + " " + login)
                        )
        );
    }
}
