package com.task.login.domain.use_case;

import com.task.login.domain.repository.UserRepository;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class LoginUseCase {
    private final UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public @NonNull Single<Object> login(String username, String password) {
        return userRepository.login(username, password);
    }
}