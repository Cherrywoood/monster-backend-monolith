package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.model.InfectedThingEntity;
import ru.itmo.monsters.repository.InfectedThingRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InfectedThingService {

    private final InfectedThingRepository infectedThingRepository;

    private final String EXC_MES_ID = "none infected thing was found by id";


    public InfectedThingEntity findById(UUID infectedThingId) {
        return infectedThingRepository.findById(infectedThingId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + infectedThingId)
        );
    }
}
