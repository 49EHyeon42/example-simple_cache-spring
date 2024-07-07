package dev.ehyeon.cache.repository;

import dev.ehyeon.cache.entity.MovieEntity;
import dev.ehyeon.cache.entity.MovieLikeEntity;
import dev.ehyeon.cache.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieLikeJpaRepository extends JpaRepository<MovieLikeEntity, Long> {

    Optional<MovieLikeEntity> findByUserEntityAndMovieEntity(UserEntity userEntity, MovieEntity movieEntity);

    boolean existsByUserEntityAndMovieEntity(UserEntity userEntity, MovieEntity movieEntity);
}
