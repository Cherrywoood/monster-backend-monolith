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

    private final String EXC_EXIST_EMAIL = "monster with this email already exists";
    private final String EXC_EXIST_USER = "monster with this userId already exists";
    private final String EXC_MES_ID = "none monster was found by id";

    public MonsterEntity save(MonsterDTO monsterDTO) {
        if (monsterRepository.findByEmail(monsterDTO.getEmail()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST_EMAIL + ": " + monsterDTO.getEmail());
        }
        if(monsterRepository.findByUserId(monsterDTO.getUserId()).isPresent()){
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

    public Map<MonsterEntity, Integer> getRating() {
        Map<MonsterEntity, Integer> rating = new HashMap<>();
        List<MonsterEntity> monsters = monsterRepository.findAll();
        for (MonsterEntity monster : monsters) {
            int countBalloons = (int) electricBalloonRepository.findAllByMonsterId(monster.getId()).stream().count();
            rating.put(monster, countBalloons);
        }
        return rating;
    }

    public List<MonsterEntity> findAllByJob(Job job) {
        return monsterRepository.findAllByJob(job);
    }

    public Optional<MonsterEntity> findAllByDateOfFearAction(Date date) {
        Optional<MonsterEntity> monsters = monsterRepository.findAllByDateOfFearAction(date);
        return monsters;
    }

    public Optional<MonsterEntity> findAllByInfectionDate(Date date) {
        Optional<MonsterEntity> monsters = monsterRepository.findAllByInfectionDate(date);
        return monsters;
    }

    public MonsterEntity updateById(UUID monsterId, MonsterDTO monsterDTO) {
        monsterRepository.findById(monsterId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + monsterId)
        );
        monsterDTO.setId(monsterId);
        return monsterRepository.save(monsterMapper.mapDtoToEntity(monsterDTO));
    }

    public void delete(UUID monsterId) {
        monsterRepository.findById(monsterId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + monsterId)
        );
        monsterRepository.deleteById(monsterId);
    }
}
