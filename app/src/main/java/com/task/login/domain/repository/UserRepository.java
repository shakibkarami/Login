package com.task.login.domain.repository;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface UserRepository {
    @NonNull Single<Object> login(String username, String password);
}