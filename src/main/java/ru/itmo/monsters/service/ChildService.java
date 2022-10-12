package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.mapper.ChildMapper;
import ru.itmo.monsters.model.*;
import ru.itmo.monsters.repository.ChildRepository;
import ru.itmo.monsters.repository.DoorRepository;

import javax.persistence.EntityExistsException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final DoorRepository doorRepository;
    private final ChildMapper childMapper;

    public ChildEntity save(ChildDTO childDTO) {

        DoorEntity doorEntity;
        if (doorRepository.findById(childDTO.getDoorId()).isPresent()) {
            doorEntity = doorRepository.findById(childDTO.getDoorId()).get();
        } else {
            doorEntity = new DoorEntity();
        }
        ChildEntity childEntity = childMapper.mapDtoToEntity(childDTO, doorEntity);
        return childRepository.save(childEntity);
    }

    public List<ChildEntity> getAll() {
        return childRepository.findAll();
    }

    public List<ChildEntity> getScaredChildrenByDate(Date date) {
        return childRepository.findAllScaredChildrenByDate(date);
    }
}
