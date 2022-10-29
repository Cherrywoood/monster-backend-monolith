package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.enums.Job;
import ru.itmo.monsters.mapper.MonsterMapper;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.repository.ElectricBalloonRepository;
import ru.itmo.monsters.repository.MonsterRepository;

import javax.persistence.EntityExistsException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MonsterService {

    private final MonsterRepository monsterRepository;
    private final ElectricBalloonRepository electricBalloonRepository;
    private final MonsterMapper monsterMapper;

    private static final String EXC_EXIST_EMAIL = "monster with this email already exists";
    private static final String EXC_EXIST_USER = "monster with this userId already exists";
    private static final String EXC_MES_ID = "none monster was found by id";

    public MonsterEntity save(MonsterDTO monsterDTO) {
        if (monsterRepository.findByEmail(monsterDTO.getEmail()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST_EMAIL + ": " + monsterDTO.getEmail());
        }
        if (monsterRepository.findByUserId(monsterDTO.getUserId()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST_USER + ": " + monsterDTO.getUserId());
        }
        return monsterRepository.save(monsterMapper.mapDtoToEntity(monsterDTO));
    }

    public Page<MonsterEntity> findAll(int page, int size) {
        return monsterRepository.findAll(PageRequest.of(page, size));
    }

    public MonsterEntity findById(UUID monsterId) {
        return monsterRepository.findById(monsterId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + monsterId)
        );
    }

    public MonsterEntity updateJobById(Job job, UUID monsterId) {
        MonsterEntity monsterEntity = monsterRepository.findById(monsterId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + monsterId)
        );
        monsterEntity.setJob(job);
        return monsterRepository.save(monsterEntity);
    }

    public Map<MonsterEntity, Integer> getRating(int page, int size) {
        Map<MonsterEntity, Integer> rating = new HashMap<>();
        List<MonsterEntity> monsters = monsterRepository.findAll();
        for (MonsterEntity monster : monsters) {
            int countBalloons = (int) electricBalloonRepository.findAllByMonsterId(monster.getId(), PageRequest.of(page, size)).stream().count();
            rating.put(monster, countBalloons);
        }
        return rating;
    }

    public Page<MonsterEntity> findAllByJob(Job job, int page, int size) {
        return monsterRepository.findAllByJob(job, PageRequest.of(page, size));
    }

    public Page<MonsterEntity> findAllByDateOfFearAction(Date date, int page, int size) {
        return monsterRepository.findAllByDateOfFearAction(date, PageRequest.of(page, size));
    }

    public Page<MonsterEntity> findAllByInfectionDate(Date date, int page, int size) {
        return monsterRepository.findAllByInfectionDate(date, PageRequest.of(page, size));
    }

    public MonsterEntity updateById(UUID monsterId, MonsterDTO monsterDTO) {
        if (monsterRepository.findById(monsterId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + monsterId);
        }
        monsterDTO.setId(monsterId);
        return monsterRepository.save(monsterMapper.mapDtoToEntity(monsterDTO));
    }

    public void delete(UUID monsterId) {
        if (monsterRepository.findById(monsterId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + monsterId);
        }
        monsterRepository.deleteById(monsterId);
    }
}
