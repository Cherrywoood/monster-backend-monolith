package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.DoorEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoorRepository extends JpaRepository<DoorEntity, UUID> {

    List<DoorEntity> findAllByActive(boolean isActive);
}
