package ru.itmo.monsters.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.conroller.exception.NotFoundException;
import ru.itmo.monsters.dto.UserRequestDTO;
import ru.itmo.monsters.mapper.UserMapper;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.repository.UserRepository;

import javax.persistence.EntityExistsException;
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

    public UserEntity save(UserRequestDTO userRequestDTO) { //регистрация
        if (userRepository.findByLogin(userRequestDTO.getLogin()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST);
        }

        RoleEntity roleEntity = roleService.findByName(userRequestDTO.getRole());
        UserEntity userEntity = mapper.mapDtoToEntity(userRequestDTO, roleEntity);
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

    public Page<UserEntity> findAll(int page, int size, String role) {
        Pageable pageable = PageRequest.of(page, size);
        if (role != null) {
            RoleEntity roleEntity = roleService.findByName(role);
            return userRepository.findAllByRole(roleEntity, pageable);
        } else return userRepository.findAll(pageable);
    }

    public UserEntity updateById(Map<String, String> updates, UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + " " + id)
        );
        return userRepository.save(
                mapper.update(updates, userEntity, roleService)
        );
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
