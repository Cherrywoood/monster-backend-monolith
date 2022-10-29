package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.mapper.ChildMapper;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.repository.ChildRepository;

import java.sql.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChildService {

    private static final String EXC_MES_ID = "none child was found by id";
    private final ChildRepository childRepository;
    private final DoorService doorService;
    private final ChildMapper childMapper;

    @Transactional
    public ChildEntity save(ChildDTO childDTO) {
        DoorEntity doorEntity;
        UUID doorId = childDTO.getDoorId();
        if (doorId == null) {
            doorEntity = doorService.save(new DoorEntity());
        } else {
            doorEntity = doorService.findById(doorId);
        }
        ChildEntity childEntity = childMapper.mapDtoToEntity(childDTO, doorEntity);
        return childRepository.save(childEntity);
    }

    public Page<ChildEntity> getAll(int page, int size) {
        return childRepository.findAll(PageRequest.of(page, size));
    }

    public Page<ChildEntity> getScaredChildrenByDate(int page, int size, Date date) {
        Pageable pageable = PageRequest.of(page, size);
        return childRepository.findAllScaredChildrenByDate(date, pageable);
    }

    public void delete(UUID id) {
        childRepository.delete(
                childRepository.findById(id).orElseThrow(
                        () -> new NotFoundException(EXC_MES_ID + ": " + id)
                )
        );
    }
}
