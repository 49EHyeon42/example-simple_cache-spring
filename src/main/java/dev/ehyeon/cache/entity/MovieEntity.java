package dev.ehyeon.cache.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "movieEntity", fetch = FetchType.LAZY)
    private List<MovieLikeEntity> movieLikeEntities;

    private String title;

    private long views;

    public MovieEntity(String title) {
        this.movieLikeEntities = new ArrayList<>();
        this.title = title;
    }

    public void increaseViews() {
        views++;
    }
}
