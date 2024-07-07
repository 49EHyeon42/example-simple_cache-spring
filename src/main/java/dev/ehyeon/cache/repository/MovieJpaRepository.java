package dev.ehyeon.cache.repository;

import dev.ehyeon.cache.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {

    // 조회 수가 같은 경우 id가 큰 값이 우선순위를 갖는다.
    List<MovieEntity> findTop3ByOrderByViewsDesc();
}
