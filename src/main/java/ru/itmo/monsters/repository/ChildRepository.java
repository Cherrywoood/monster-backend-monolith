package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.MonsterEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, UUID> {

    @Query(value = "select c from ChildEntity c " +
            "join DoorEntity " +
            "on c.door=DoorEntity.id " +
            "join FearActionEntity " +
            "on DoorEntity.id=FearActionEntity.doorEntity.id " +
            "where FearActionEntity.date=:date", nativeQuery = true)
    List<ChildEntity> findAllScaredChildrenByDate(@Param("date") Date date);

}
