package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.FearActionEntity;

import java.util.UUID;

@Repository
public interface FearActionRepository extends JpaRepository<FearActionEntity, UUID> {
}
