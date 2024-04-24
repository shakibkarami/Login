package com.task.login.domain.repository;

import io.reactivex.rxjava3.core.Completable;

public interface UserRepository {
    Completable login(String username, String password);
}