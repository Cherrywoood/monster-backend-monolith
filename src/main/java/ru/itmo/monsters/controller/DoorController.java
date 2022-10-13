package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.DoorDTO;
import ru.itmo.monsters.mapper.DoorMapper;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.service.DoorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARE ASSISTANT')")
@RequestMapping("doors")
public class DoorController {
    private final DoorService doorService;
    private final DoorMapper doorMapper;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARE') or hasAuthority('SCARE ASSISTANT')")
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<DoorDTO> getActiveDoors() {
        List<DoorEntity> doorEntities = doorService.findAllActiveDoors();
        return doorEntities
                .stream()
                .map((doorMapper::mapEntityToDto))
                .toList();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARE ASSISTANT')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoorDTO changeDoor(@PathVariable UUID id) {
        return doorMapper.mapEntityToDto(doorService.changeActive(id));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARE ASSISTANT')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDoor(@PathVariable UUID id) {
        doorService.deleteDoor(id);
    }

}
