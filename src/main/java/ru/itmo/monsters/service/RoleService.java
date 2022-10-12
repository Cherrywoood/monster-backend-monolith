package ru.itmo.monsters.service;

import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.mapper.RoleMapper;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private static final String EXC_MES_NAME = "role not found by name";
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository roleRepository, RoleMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public RoleEntity save(RoleDTO roleDTO) {
        RoleEntity roleEntity = mapper.mapDtoToEntity(roleDTO);
        roleRepository.save(roleEntity);
        return roleEntity;
    }

    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(EXC_MES_NAME + " " + name)
        );
    }

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public void deleteByName(String name) {
        roleRepository.delete(
                roleRepository
                        .findByName(name)
                        .orElseThrow(
                                () -> new NotFoundException(EXC_MES_NAME + " " + name)
                        )
        );
    }


}
