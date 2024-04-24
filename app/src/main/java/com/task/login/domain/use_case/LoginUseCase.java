package com.task.login.domain.use_case;

import com.task.login.domain.repository.UserRepository;

import io.reactivex.rxjava3.core.Completable;

public class LoginUseCase {
    private final UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable login(String username, String password) {
        return userRepository.login(username, password);
    }
}