package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.InfectionEntity;

import java.util.UUID;

@Repository
public interface InfectionRepository extends JpaRepository<InfectionEntity, UUID> {
}
