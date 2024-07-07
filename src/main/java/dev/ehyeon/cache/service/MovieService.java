package dev.ehyeon.cache.service;

import dev.ehyeon.cache.entity.MovieEntity;
import dev.ehyeon.cache.exception.MovieNotFoundException;
import dev.ehyeon.cache.repository.MovieJpaRepository;
import dev.ehyeon.cache.response.SearchMovieResponse;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MovieService {

    private final MovieJpaRepository movieJpaRepository;

    public SearchMovieResponse searchById(long id) {
        MovieEntity foundMovieEntity = movieJpaRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);

        foundMovieEntity.increaseViews();

        return new SearchMovieResponse(
                foundMovieEntity.getId(),
                foundMovieEntity.getTitle(),
                foundMovieEntity.getViews());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "searchTop3")
    public List<SearchMovieResponse> searchTop3() {
        List<MovieEntity> foundMovieEntities = movieJpaRepository.findTop3ByOrderByViewsDesc();

        return foundMovieEntities.stream()
                .map(movieEntity -> new SearchMovieResponse(
                        movieEntity.getId(),
                        movieEntity.getTitle(),
                        movieEntity.getViews()))
                .toList();
    }

    @Scheduled(cron = "*/30 * * * * *")
    @CacheEvict(value = "searchTop3", allEntries = true)
    public void evictCache() {
        log.info("MovieService: evict searchTop3 cache");
    }

    @PostConstruct
    private void postConstruct() {
        movieJpaRepository.save(new MovieEntity("영화 1"));
        movieJpaRepository.save(new MovieEntity("영화 2"));
        movieJpaRepository.save(new MovieEntity("영화 3"));
        movieJpaRepository.save(new MovieEntity("영화 4"));
        movieJpaRepository.save(new MovieEntity("영화 5"));
    }
}
