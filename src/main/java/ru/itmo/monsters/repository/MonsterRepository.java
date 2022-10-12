package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.enums.Job;
import ru.itmo.monsters.model.MonsterEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonsterRepository extends JpaRepository<MonsterEntity, UUID> {

    Optional<MonsterEntity> findAllByJob(Job job);

    Optional<MonsterEntity> findByEmail(String email);

    @Query(value = "select m from MonsterEntity m " +
            "join FearActionEntity " +
            "on m.id=FearActionEntity.monsterEntity.id " +
            "where FearActionEntity.date=:date", nativeQuery = true)
    Optional<MonsterEntity> findAllByDateOfFearAction(@Param("date") Date date);

    @Query(value = "select m from MonsterEntity m " +
            "join InfectionEntity " +
            "on m.id=InfectionEntity.monster.id  " +
            "where InfectionEntity.infectionDate<=:date " +
            "and InfectionEntity.cureDate>:date", nativeQuery = true)
    Optional<MonsterEntity> findAllByInfectionDate(@Param("date") Date date);

}
