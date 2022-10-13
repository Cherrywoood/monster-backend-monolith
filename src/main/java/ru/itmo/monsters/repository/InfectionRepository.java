package ru.itmo.monsters.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.InfectionEntity;
import ru.itmo.monsters.model.MonsterEntity;

import java.util.UUID;

@Repository
public interface InfectionRepository extends JpaRepository<InfectionEntity, UUID> {
    Page<InfectionEntity> findAllByMonster(MonsterEntity monster, Pageable pageable);

    @EntityGraph(attributePaths = {"infectedThing","monster"})
    Page<InfectionEntity> findAll(Pageable pageable);
}
