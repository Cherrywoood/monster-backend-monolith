package ru.itmo.monsters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.ChildEntity;

import java.util.UUID;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, UUID> {
}
