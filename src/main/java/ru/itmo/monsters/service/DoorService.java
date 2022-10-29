package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.repository.DoorRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DoorService {

    private final DoorRepository doorRepository;

    private static final String EXC_MES_ID = "none door was found by id";

    public DoorEntity findById(UUID doorId) {
        return doorRepository.findById(doorId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + doorId)
        );
    }

    public DoorEntity save(DoorEntity doorEntity) {
        return doorRepository.save(doorEntity);
    }

    public List<DoorEntity> findAllActiveDoors() {
        return doorRepository.findAllByActive(true);
    }

    public DoorEntity changeActive(UUID id) {
        DoorEntity doorEntity = doorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + " " + id)
        );
        doorEntity.setActive(!doorEntity.isActive());
        doorRepository.save(doorEntity);
        return doorEntity;
    }

    public void deleteDoor(UUID id) {
        doorRepository.delete(
                doorRepository.findById(id).orElseThrow(
                        () -> new NotFoundException(EXC_MES_ID + " " + id)
                )
        );
    }
}
