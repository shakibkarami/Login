package com.task.login.data;

import com.task.login.data.remote.api.AuthService;
import com.task.login.domain.model.TokenResponse;
import com.task.login.domain.repository.UserRepository;
import com.task.login.util.Encryption;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepositoryImpl implements UserRepository {

    private static final String VALID_USERNAME = "username";
    private static final String VALID_PASSWORD = "password";
    private AuthService authService;
    private static final String BASE_URL = "https://base-url.com/";
    private String hardcodedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\n";
    Encryption encryption = Encryption.getInstance();

    public UserRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authService = retrofit.create(AuthService.class);
    }

    // Here is implementation of login method using retrofit

//    @Override
//    public Completable login(String username, String password) {
//        return Completable.create(emitter -> {
//            authService.authenticate(username, password).enqueue(new Callback<TokenResponse>() {
//                @Override
//                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
//                    if (response.isSuccessful()) {
//                        // Extract token from response body
//                        TokenResponse tokenResponse = response.body();
//                        String token = tokenResponse.getToken();
//                        emitter.onComplete();
//                    } else {
//                        emitter.onError(new RuntimeException("Invalid username or password"));
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TokenResponse> call, Throwable t) {
//                    emitter.onError(t);
//                }
//            });
//        }).subscribeOn(Schedulers.io());
//    }


    @Override
    public @NonNull Single<Object> login(String username, String password) {
        return Single.create(emitter -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                emitter.onError(e);
                return;
            }

            if (isValidCredentials(username, password)) {
                TokenResponse tokenResponse = new TokenResponse(encryption.encrypt(hardcodedToken));
                String token = tokenResponse.getToken();
                emitter.onSuccess(token);
            } else {
                emitter.onError(new RuntimeException("Invalid username or password"));
            }
        }).subscribeOn(Schedulers.io());
    }

    private boolean isValidCredentials(String username, String password) {
        return VALID_USERNAME.equals(encryption.decrypt(encryption.encrypt(username))) && VALID_PASSWORD.equals(encryption.decrypt(encryption.encrypt(password)));
    }
}