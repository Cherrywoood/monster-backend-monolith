package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.model.DoorEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoorRepository extends JpaRepository<DoorEntity, UUID> {

    List<DoorEntity> findAllByIsActive(boolean isActive);
}
