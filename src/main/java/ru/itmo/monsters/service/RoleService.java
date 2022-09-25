package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.mapper.RoleMapper;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.repository.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    public RoleEntity save(RoleDTO roleDTO) {
        RoleEntity roleEntity = mapper.mapDtoToEntity(roleDTO);
        roleRepository.save(roleEntity);
        return roleEntity;
    }

    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(IllegalAccessError::new);
    }

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public void deleteByName(String name) {
        roleRepository.delete(
                roleRepository
                        .findByName(name)
                        .orElseThrow(IllegalAccessError::new)
        );
    }


}
