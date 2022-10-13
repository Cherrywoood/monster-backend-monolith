package ru.itmo.monsters.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.DoorEntity;
import ru.itmo.monsters.model.InfectedThingEntity;

import java.util.UUID;

@Repository
public interface InfectedThingRepository extends JpaRepository<InfectedThingEntity, UUID> {
    Page<InfectedThingEntity> findAllByDoor(DoorEntity doorEntity, Pageable pageable);
}
