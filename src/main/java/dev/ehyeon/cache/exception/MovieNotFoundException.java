package dev.ehyeon.cache.exception;

import org.springframework.http.HttpStatus;

public class MovieNotFoundException extends CustomException {

    public MovieNotFoundException() {
        super("MovieNotFound", HttpStatus.NOT_FOUND);
    }
}
