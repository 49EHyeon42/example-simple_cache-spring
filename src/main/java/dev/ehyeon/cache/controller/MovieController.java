package dev.ehyeon.cache.controller;

import dev.ehyeon.cache.exception.MovieNotFoundException;
import dev.ehyeon.cache.response.SearchMovieResponse;
import dev.ehyeon.cache.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public SearchMovieResponse searchById(@PathVariable("id") Long id) {
        return movieService.searchById(id);
    }

    @GetMapping("/top-3")
    public List<SearchMovieResponse> searchAllTop3() {
        return movieService.searchTop3();
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleMovieNotFoundException(MovieNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
