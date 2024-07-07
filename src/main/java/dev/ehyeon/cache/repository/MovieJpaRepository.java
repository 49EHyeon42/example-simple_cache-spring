package dev.ehyeon.cache.repository;

import dev.ehyeon.cache.entity.MovieEntity;
import dev.ehyeon.cache.response.SearchMovieResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {

    // 조회 수가 같은 경우 id가 큰 값이 우선순위를 갖는다.
    List<MovieEntity> findTop3ByOrderByViewsDesc();

    @Query("SELECT new dev.ehyeon.cache.response.SearchMovieResponse(m.id, m.title, m.views, SIZE(m.movieLikeEntities)) " +
            "FROM MovieEntity m " +
            "ORDER BY m.views DESC, m.id DESC")
    List<SearchMovieResponse> findOrderByLikesDesc(Pageable pageable);
}
