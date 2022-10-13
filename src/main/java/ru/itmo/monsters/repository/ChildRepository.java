package ru.itmo.monsters.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;

import java.util.Date;
import java.util.UUID;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, UUID> {

//    @Query(value = "select * from child c join door on c.door_id=door.id join fear_action on door.id=fear_action.door_id where fear_action.date=:date", nativeQuery = true)
//    List<ChildEntity> findAllScaredChildrenByDate(@Param("date") Date date);

    @Query(value = "select * from child c join door on c.door_id=door.id join fear_action on door.id=fear_action.door_id where fear_action.date=:date", nativeQuery = true)
    Page<ChildEntity> findAllScaredChildrenByDate(@Param("date") Date date, Pageable pageable);

    void deleteByDoor(DoorEntity doorEntity);

}
