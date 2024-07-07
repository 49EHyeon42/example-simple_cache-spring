package dev.ehyeon.cache.service;

import dev.ehyeon.cache.entity.UserEntity;
import dev.ehyeon.cache.repository.UserJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    @PostConstruct
    private void postConstruct() {
        userJpaRepository.save(new UserEntity("사용자 1"));
        userJpaRepository.save(new UserEntity("사용자 2"));
        userJpaRepository.save(new UserEntity("사용자 3"));
    }
}
