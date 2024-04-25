package com.task.login.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.task.login.data.UserRepositoryImpl;
import com.task.login.domain.model.InputValidationType;
import com.task.login.domain.repository.UserRepository;
import com.task.login.domain.use_case.LoginUseCase;
import com.task.login.domain.use_case.ValidateInputUseCase;
import com.task.login.presentation.state.LoginState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ValidateInputUseCase validateInputUseCase = new ValidateInputUseCase();

    UserRepository userRepository = new UserRepositoryImpl();
    LoginUseCase loginUseCase = new LoginUseCase(userRepository);


    public LoginViewModel(Application application) {
        super(application);
    }

    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public void login(String username, String password) {
        if (checkInputValidation(username, password)) {
            loginState.setValue(new LoginState(true, false, false, null));
            disposable.add(loginUseCase.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> loginState.setValue(new LoginState(false, true, false, null)),
                            throwable -> loginState.setValue(new LoginState(false, false, true, "Invalid Username or password"))
                    ));
        }
    }

    private Boolean checkInputValidation(String username, String password){
        InputValidationType validationResult = validateInputUseCase.invoke(
                username,
                password
        );
        return processInputValidationType(validationResult);
    }

    private boolean processInputValidationType(InputValidationType result){
        if (result == InputValidationType.EmptyInput){
            loginState.setValue(new LoginState(false, false, true, "Inputs can't be empty"));
            return false;
        }

        if (result == InputValidationType.ShortPassword) {
            loginState.setValue(new LoginState(false, false, true, "Password should be at least 8 characters"));
            return false;
        }

        return true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}