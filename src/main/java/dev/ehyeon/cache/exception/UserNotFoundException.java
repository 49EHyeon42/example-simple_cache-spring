package dev.ehyeon.cache.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super("UserNotFound", HttpStatus.NOT_FOUND);
    }
}
