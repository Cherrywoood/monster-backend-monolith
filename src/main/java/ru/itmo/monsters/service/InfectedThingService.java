package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.InfectedThingDTO;
import ru.itmo.monsters.mapper.InfectedThingMapper;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.model.InfectedThingEntity;
import ru.itmo.monsters.repository.InfectedThingRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InfectedThingService {

    private final InfectedThingRepository infectedThingRepository;
    private final DoorService doorService;
    private final InfectedThingMapper mapper;

    private static final String EXC_MES_ID = "none infected thing was found by id ";

    public InfectedThingEntity save(InfectedThingDTO infectedThingDTO) {
        DoorEntity doorEntity = doorService.findById(infectedThingDTO.getDoorId());
        return infectedThingRepository.save(mapper.mapDtoToEntity(infectedThingDTO, doorEntity));
    }

    public InfectedThingEntity findById(UUID infectedThingId) {
        return infectedThingRepository.findById(infectedThingId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + infectedThingId)
        );
    }

    public Page<InfectedThingEntity> findAll(int page, int size, UUID doorId) {
        Pageable pageable = PageRequest.of(page, size);
        if (doorId != null) {
            DoorEntity doorEntity = doorService.findById(doorId);
            return infectedThingRepository.findAllByDoor(doorEntity, pageable);
        } else return infectedThingRepository.findAll(pageable);
    }

    public void delete(UUID infectedThingId) {
        infectedThingRepository.delete(
                infectedThingRepository.findById(infectedThingId).orElseThrow(
                        () -> new NotFoundException(EXC_MES_ID + ": " + infectedThingId)
                )
        );
    }

}
