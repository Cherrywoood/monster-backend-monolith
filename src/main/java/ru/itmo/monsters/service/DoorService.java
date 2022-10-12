package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.repository.DoorRepository;
import ru.itmo.monsters.repository.FearActionRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DoorService {

    private final DoorRepository doorRepository;

    private final String EXC_MES_ID = "none door was found by id";


    public DoorEntity findById(UUID doorId) {
        return doorRepository.findById(doorId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + doorId)
        );
    }
}
