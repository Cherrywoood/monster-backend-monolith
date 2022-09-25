package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.mapper.RoleMapper;
import ru.itmo.monsters.service.RoleService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO save(@RequestBody RoleDTO roleDTO) {
        return mapper.mapEntityToDto(roleService.save(roleDTO));
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO findByName(@PathVariable String name) {
        return mapper.mapEntityToDto(roleService.findByName(name));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDTO> findAll() {
        return roleService.findAll()
                .stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        roleService.deleteByName(name);
    }

}
