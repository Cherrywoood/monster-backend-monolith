package ru.itmo.monsters.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select e from ElectricBalloonEntity e " +
            "join e.fearActionEntity f " +
            "where f.date=:date")
    Page<ElectricBalloonEntity> findAllFilledByDate(@Param("date") Date date, Pageable pageable);

    @Query("select e from ElectricBalloonEntity e " +
            "join e.fearActionEntity f " +
            "where f.date=:date " +
            "and e.cityEntity.id=:cityId")
    Page<ElectricBalloonEntity> findAllFilledByDateAndCity(@Param("date") Date date, @Param("cityId") UUID cityId, Pageable pageable);


    @Query("select e from ElectricBalloonEntity e " +
            "join e.fearActionEntity f " +
            "where f.monsterEntity.id=:monsterId")
    Page<ElectricBalloonEntity> findAllByMonsterId(@Param("monsterId") UUID monsterId, Pageable pageable);

}

