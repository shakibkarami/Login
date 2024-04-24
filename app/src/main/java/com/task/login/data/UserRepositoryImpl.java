package com.task.login.data;

import com.task.login.domain.repository.UserRepository;

import io.reactivex.rxjava3.core.Completable;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Completable login(String username, String password) {
        // TODO: complete login function later
        return null;
    }
}