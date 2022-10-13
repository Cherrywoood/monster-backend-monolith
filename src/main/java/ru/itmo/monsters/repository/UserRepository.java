package ru.itmo.monsters.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.monsters.model.RoleEntity;
import ru.itmo.monsters.model.UserEntity;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);

    Page<UserEntity> findAllByRole(RoleEntity role, Pageable pageable);

    @EntityGraph(attributePaths = {"role"})
    Page<UserEntity> findAll(Pageable pageable);


}
