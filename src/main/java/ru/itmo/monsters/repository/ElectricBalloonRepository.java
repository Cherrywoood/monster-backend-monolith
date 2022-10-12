package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.ElectricBalloonEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ElectricBalloonRepository extends JpaRepository<ElectricBalloonEntity, UUID> {

    @Query("select e from ElectricBalloonEntity e " +
            "join e.fearActionEntity f " +
            "where f.date=:date")
    Optional<ElectricBalloonEntity> findAllFilledByDate(@Param("date") Date date);

    @Query("select e from ElectricBalloonEntity e " +
            "join e.fearActionEntity f " +
            "where f.date=:date " +
            "and e.cityEntity.id=:cityId")
    Optional<ElectricBalloonEntity> findAllFilledByDateAndCity(@Param("date") Date date, @Param("cityId") UUID cityId);


    @Query("select e from ElectricBalloonEntity e " +
            "join e.fearActionEntity f " +
            "where f.monsterEntity.id=:monsterId")
    Optional<ElectricBalloonEntity> findAllByMonsterId(@Param("monsterId") UUID monsterId);

}

