package dev.ehyeon.cache.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MovieLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private MovieEntity movieEntity;

    public MovieLikeEntity(UserEntity userEntity, MovieEntity movieEntity) {
        this.userEntity = userEntity;
        this.movieEntity = movieEntity;
    }
}
