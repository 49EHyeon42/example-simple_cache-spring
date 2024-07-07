package dev.ehyeon.cache.exception;

import org.springframework.http.HttpStatus;

public class AlreadyLikedException extends CustomException {

    public AlreadyLikedException() {
        super("AlreadyLiked", HttpStatus.BAD_REQUEST);
    }
}
