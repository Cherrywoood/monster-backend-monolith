package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.mapper.DoorMapper;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.repository.DoorRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DoorService {

    private final DoorRepository doorRepository;
    private final DoorMapper doorMapper;

    private final String EXC_MES_ID = "none door was found by id";


    public DoorEntity findById(UUID doorId) {
        return doorRepository.findById(doorId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + doorId)
        );
    }

    public DoorEntity save(UUID id) {
        if (doorRepository.findById(id).isPresent()) {
            return doorRepository.findById(id).get();
        }
        return doorRepository.save(new DoorEntity());
    }

    public List<DoorEntity> findAllActiveDoors() {
        return doorRepository.findAllByIsActive(true);
    }
}
