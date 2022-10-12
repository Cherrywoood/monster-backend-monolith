package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.conroller.exception.NotFoundException;
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

    private final String EXC_EXIST = "monster with this email already exists";
    private final String EXC_MES_ID = "none monster was found by id";

    public MonsterEntity save(MonsterDTO monsterDTO) {
        if (monsterRepository.findByEmail(monsterDTO.getEmail()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST + ": " + monsterDTO.getEmail());
        }
        return monsterRepository.save(monsterMapper.mapDtoToEntity(monsterDTO));
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

    public Optional<MonsterEntity> findAllByJob(Job job) {
        Optional<MonsterEntity> monsters = monsterRepository.findAllByJob(job);
        return monsters;
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
