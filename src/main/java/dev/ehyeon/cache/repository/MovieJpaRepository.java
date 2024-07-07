package dev.ehyeon.cache.repository;

import dev.ehyeon.cache.entity.MovieEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {

    // 조회 수가 같은 경우 id가 큰 값이 우선순위를 갖는다.
    List<MovieEntity> findTop3ByOrderByViewsDesc();

    // fixme: 같은 likes가 있을 때 id가 낮은순으로 조회, 통일 필요
    @Query("SELECT m FROM MovieEntity m ORDER BY SIZE(m.movieLikeEntities) DESC")
    List<MovieEntity> findOrderByLikesDesc(Pageable pageable);
}
