package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.enums.Job;
import ru.itmo.monsters.model.MonsterEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonsterRepository extends JpaRepository<MonsterEntity, UUID> {

    Optional<MonsterEntity> findAllByJob(Job job);

    Optional<MonsterEntity> findByEmail(String email);

    @Query("select m from MonsterEntity m " +
            "join m.fearActions f " +
            "where f.date=:date")
    Optional<MonsterEntity> findAllByDateOfFearAction(@Param("date") Date date);

    @Query("select m from MonsterEntity m " +
            "join m.infections i " +
            "where i.infectionDate<=:date " +
            "and i.cureDate>:date")
    Optional<MonsterEntity> findAllByInfectionDate(@Param("date") Date date);


}
