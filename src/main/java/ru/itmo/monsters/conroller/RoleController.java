package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.mapper.RoleMapper;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.service.RoleService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@RestController
@Validated
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final PageMapper<RoleDTO> pageMapper;
    private final RoleMapper roleMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO save(@Valid @RequestBody RoleDTO roleDTO) {
        return roleMapper.mapEntityToDto(roleService.save(roleDTO));
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO findByName(@PathVariable String name) {
        return roleMapper.mapEntityToDto(roleService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<PageDTO<RoleDTO>> findAll(@RequestParam(defaultValue = "0")
                                                    @Min(value = 0, message = "must not be less than zero") int page,
                                                    @RequestParam(defaultValue = "5")
                                                    @Max(value = 50, message = "must not be more than 50 characters") int size) {
        Page<RoleEntity> pageRoles = roleService.findAll(page, size);
        if (pageRoles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(pageMapper.mapToDto(pageRoles.map(roleMapper::mapEntityToDto)), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        roleService.deleteByName(name);
    }

}
