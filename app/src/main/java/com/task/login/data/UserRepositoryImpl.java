package com.task.login.data;

import com.task.login.domain.repository.UserRepository;
import com.task.login.util.Encryption;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepositoryImpl implements UserRepository {

    private static final String VALID_USERNAME = "username";
    private static final String VALID_PASSWORD = "password";
    Encryption encryption = Encryption.getInstance();

    @Override
    public Completable login(String username, String password) {
        return Completable.create(emitter -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                emitter.onError(e);
                return;
            }

            if (isValidCredentials(username, password)) {
                emitter.onComplete();
            } else {
                emitter.onError(new RuntimeException("Invalid username or password"));
            }
        }).subscribeOn(Schedulers.io());
    }

    private boolean isValidCredentials(String username, String password) {
        return VALID_USERNAME.equals(encryption.decrypt(encryption.encrypt(username))) && VALID_PASSWORD.equals(encryption.decrypt(encryption.encrypt(password)));
    }
}