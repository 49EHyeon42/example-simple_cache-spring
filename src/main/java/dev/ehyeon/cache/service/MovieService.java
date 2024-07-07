package dev.ehyeon.cache.service;

import dev.ehyeon.cache.entity.MovieEntity;
import dev.ehyeon.cache.entity.MovieLikeEntity;
import dev.ehyeon.cache.entity.UserEntity;
import dev.ehyeon.cache.exception.AlreadyLikedException;
import dev.ehyeon.cache.exception.MovieNotFoundException;
import dev.ehyeon.cache.exception.NotLikedException;
import dev.ehyeon.cache.exception.UserNotFoundException;
import dev.ehyeon.cache.repository.MovieJpaRepository;
import dev.ehyeon.cache.repository.MovieLikeJpaRepository;
import dev.ehyeon.cache.repository.UserJpaRepository;
import dev.ehyeon.cache.response.SearchMovieResponse;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
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
    private final MovieLikeJpaRepository movieLikeJpaRepository;
    private final UserJpaRepository userJpaRepository;

    // fixme: 1+N 문제 발생

    public SearchMovieResponse searchById(long id) {
        MovieEntity foundMovieEntity = movieJpaRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);

        foundMovieEntity.increaseViews();

        return new SearchMovieResponse(
                foundMovieEntity.getId(),
                foundMovieEntity.getTitle(),
                foundMovieEntity.getViews(),
                foundMovieEntity.getMovieLikeEntities().size());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "searchTop3")
    public List<SearchMovieResponse> searchTop3() {
        return movieJpaRepository.findOrderByViewsDesc(PageRequest.of(0, 3));
    }

    @Transactional(readOnly = true)
    public List<SearchMovieResponse> searchTop3ByLikes() {
        return movieJpaRepository.findOrderByLikesDesc(PageRequest.of(0, 3));
    }

    public void checkLike(long userId, long movieId) {
        UserEntity foundUserEntity = userJpaRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        MovieEntity foundMovieEntity = movieJpaRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        if (movieLikeJpaRepository.existsByUserEntityAndMovieEntity(foundUserEntity, foundMovieEntity)) {
            throw new AlreadyLikedException();
        }

        movieLikeJpaRepository.save(new MovieLikeEntity(foundUserEntity, foundMovieEntity));
    }

    public void uncheckLike(long userId, long movieId) {
        UserEntity foundUserEntity = userJpaRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        MovieEntity foundMovieEntity = movieJpaRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        MovieLikeEntity foundMovieLikeEntity = movieLikeJpaRepository.findByUserEntityAndMovieEntity(foundUserEntity, foundMovieEntity)
                .orElseThrow(NotLikedException::new);

        movieLikeJpaRepository.delete(foundMovieLikeEntity);
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
