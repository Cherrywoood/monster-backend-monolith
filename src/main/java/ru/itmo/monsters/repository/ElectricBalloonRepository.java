package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.CityEntity;
import ru.itmo.monsters.model.ElectricBalloonEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ElectricBalloonRepository extends JpaRepository<ElectricBalloonEntity, UUID> {

    @Query("select e from ElectricBalloonEntity e " +
            "join FearActionEntity " +
            "on e.fearActionEntity=FearActionEntity.id " +
            "where FearActionEntity.date=?1")
    Optional<ElectricBalloonEntity> findAllBalloonsFallenByDate(Date date);

    @Query("select e from ElectricBalloonEntity e " +
            "join FearActionEntity " +
            "on e.fearActionEntity=FearActionEntity.id " +
            "where FearActionEntity.date=?1 " +
            "and e.cityEntity=?2")
    Optional<ElectricBalloonEntity> findAllBalloonsFallenByDateAndCity(Date date, CityEntity city);
}
