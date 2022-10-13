package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.dto.RoleDTO;
import ru.itmo.monsters.mapper.ChildMapper;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.service.ChildService;
import ru.itmo.monsters.service.DoorService;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("child")
public class ChildController {

    private final ChildService childService;
    private final ChildMapper childMapper;
    private final DoorService doorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChildDTO addChild(@Valid @RequestBody ChildDTO childDTO) {
        DoorEntity doorEntity = doorService.save(childDTO.getDoorId());
        return childMapper.mapEntityToDto(childService.save(childDTO, doorEntity));
    }

    @GetMapping("/children")
    @ResponseStatus(HttpStatus.OK)
    public List<ChildDTO> getChildren() {
        List<ChildEntity> childEntities = childService.getAll();
        return childEntities
                .stream()
                .map((childMapper::mapEntityToDto))
                .toList();
    }

    @GetMapping("/scared")
    @ResponseStatus(HttpStatus.OK)
    public List<ChildDTO> getScaredChildrenByDate(@RequestParam Date date) {
        List<ChildEntity> childEntities = childService.getScaredChildrenByDate(date);
        return childEntities
                .stream()
                .map((childMapper::mapEntityToDto))
                .toList();
    }

//    @GetMapping("/{name}")
//    @ResponseStatus(HttpStatus.OK)
//    public RoleDTO findByName(@PathVariable String name) {
//        return mapper.mapEntityToDto(roleService.findByName(name));
//    }

}
