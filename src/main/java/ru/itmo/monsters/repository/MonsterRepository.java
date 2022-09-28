package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.enums.Job;
import ru.itmo.monsters.model.MonsterEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonsterRepository extends JpaRepository<MonsterEntity, UUID> {

    Optional<MonsterEntity> findAllByJob(Job job);

    @Query("select m from MonsterEntity m " +
            "join FearActionEntity " +
            "on m.id=FearActionEntity.monsterEntity " +
            "where FearActionEntity.date=?1")
    Optional<MonsterEntity> findAllMonstersByDateOfFearAction(Date date);

    @Query("select m from MonsterEntity m " +
            "join InfectionEntity " +
            "on m.id=InfectionEntity.monster  " +
            "where InfectionEntity.infectionDate<=?1 " +
            "and InfectionEntity.cureDate>?1")
    Optional<MonsterEntity> findAllMonstersByDateOfInfection(Date date);
}
