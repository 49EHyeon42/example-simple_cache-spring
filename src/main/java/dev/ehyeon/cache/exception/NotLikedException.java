package dev.ehyeon.cache.exception;

import org.springframework.http.HttpStatus;

public class NotLikedException extends CustomException {

    public NotLikedException() {
        super("NotLiked", HttpStatus.BAD_REQUEST);
    }
}
