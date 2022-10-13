package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.InfectionDTO;
import ru.itmo.monsters.mapper.InfectionMapper;
import ru.itmo.monsters.model.InfectionEntity;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.repository.InfectionRepository;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InfectionService {
    private final InfectionRepository infectionRepository;
    private final MonsterService monsterService;
    private final InfectionMapper mapper;
    private static final String EXC_MES_ID = "infection not found by id ";

    public InfectionEntity save(InfectionDTO infectionDTO) {
        MonsterEntity monsterEntity = monsterService.findById(infectionDTO.getMonsterId());
        return infectionRepository.save(mapper.mapDtoToEntity(infectionDTO, monsterEntity));
    }

    public InfectionEntity findById(UUID id) {
        return infectionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + id)
        );
    }

    public InfectionEntity updateCureDate(UUID id, Map<String, Date> cureDate) {
        InfectionEntity infectionEntity = infectionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + id)
        );
        infectionEntity.setCureDate(cureDate.get("cureDate"));
        return infectionRepository.save(infectionEntity);
    }

    public Page<InfectionEntity> findAll(int page, int size, UUID monsterId) {
        Pageable pageable = PageRequest.of(page, size);
        if (monsterId != null) {
            MonsterEntity monsterEntity = monsterService.findById(monsterId);
            return infectionRepository.findAllByMonster(monsterEntity, pageable);
        } else return infectionRepository.findAll(pageable);
    }

    public void delete(UUID id) {
        infectionRepository.delete(
                infectionRepository.findById(id).orElseThrow(
                        () -> new NotFoundException(EXC_MES_ID + id)
                )
        );
    }
}
