package ru.itmo.monsters.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<MonsterEntity> findAllByJob(Job job, Pageable pageable);

    Optional<MonsterEntity> findByEmail(String email);

    @Query("select m from MonsterEntity m " +
            "join m.userEntity u " +
            "where u.id=:userId")
    Optional<MonsterEntity> findByUserId(@Param("userId") UUID userId);

    @Query("select m from MonsterEntity m " +
            "join m.fearActions f " +
            "where f.date=:date")
    Page<MonsterEntity> findAllByDateOfFearAction(@Param("date") Date date, Pageable pageable);

    @Query("select m from MonsterEntity m " +
            "join m.infections i " +
            "where i.infectionDate<=:date " +
            "and i.cureDate>:date")
    Page<MonsterEntity> findAllByInfectionDate(@Param("date") Date date, Pageable pageable);


}
