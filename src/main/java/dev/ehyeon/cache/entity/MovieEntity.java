package dev.ehyeon.cache.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private long views;

    public MovieEntity(String title) {
        this.title = title;
    }

    public void increaseViews() {
        views++;
    }
}
