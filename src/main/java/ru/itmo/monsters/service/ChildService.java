package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.mapper.ChildMapper;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.repository.ChildRepository;
import ru.itmo.monsters.repository.DoorRepository;

import java.sql.Date;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final DoorRepository doorRepository;
    private final ChildMapper childMapper;

    public ChildEntity save(ChildDTO childDTO, DoorEntity doorEntity) {
        ChildEntity childEntity = childMapper.mapDtoToEntity(childDTO, doorEntity);
        return childRepository.save(childEntity);
    }

//    public List<ChildEntity> getAll() {
//        return childRepository.findAll();
//    }

    public Page<ChildEntity> getAll(int page, int size) {
        return childRepository.findAll(PageRequest.of(page, size));
    }

//    public List<ChildEntity> getScaredChildrenByDate(Date date) {
//        return childRepository.findAllScaredChildrenByDate(date);
//    }

    public Page<ChildEntity> getScaredChildrenByDate(int page, int size, Date date) {
        Pageable pageable = PageRequest.of(page, size);
        return childRepository.findAllScaredChildrenByDate(date, pageable);
    }
}
