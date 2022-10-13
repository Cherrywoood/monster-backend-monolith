package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.dto.DoorDTO;
import ru.itmo.monsters.mapper.DoorMapper;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.service.DoorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("doors")
public class DoorController {
    private final DoorService doorService;
    private final DoorMapper doorMapper;

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<DoorDTO> getActiveDoors() {
        List<DoorEntity> doorEntities = doorService.findAllActiveDoors();
        return doorEntities
                .stream()
                .map((doorMapper::mapEntityToDto))
                .toList();
    }

}
