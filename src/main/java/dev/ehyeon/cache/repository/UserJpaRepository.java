package dev.ehyeon.cache.repository;

import dev.ehyeon.cache.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
