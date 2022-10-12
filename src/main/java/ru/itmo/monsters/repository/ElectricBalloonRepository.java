package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.ElectricBalloonEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ElectricBalloonRepository extends JpaRepository<ElectricBalloonEntity, UUID> {

    @Query(value = "select e from ElectricBalloonEntity e " +
            "join FearActionEntity " +
            "on e.fearActionEntity.id=FearActionEntity.id " +
            "where FearActionEntity.date=:date", nativeQuery = true)
    Optional<ElectricBalloonEntity> findAllFilledByDate(@Param("date") Date date);

    @Query(value = "select e from ElectricBalloonEntity e " +
            "join FearActionEntity " +
            "on e.fearActionEntity.id=FearActionEntity.id " +
            "where FearActionEntity.date=:date " +
            "and e.cityEntity.id=:city_id", nativeQuery = true)
    Optional<ElectricBalloonEntity> findAllFilledByDateAndCity(@Param("date") Date date, @Param("city_id") UUID cityId);

    @Query(value = "select e from ElectricBalloonEntity e " +
            "join FearActionEntity " +
            "on e.fearActionEntity.id=FearActionEntity.id " +
            "where FearActionEntity.monsterEntity.id=:monster_id", nativeQuery = true)
    Optional<ElectricBalloonEntity> findAllByMonsterId(@Param("monster_id") UUID monsterId);

}

