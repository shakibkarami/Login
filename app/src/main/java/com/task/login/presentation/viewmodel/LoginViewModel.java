package com.task.login.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.login.domain.model.InputValidationType;
import com.task.login.domain.use_case.LoginUseCase;
import com.task.login.domain.use_case.ValidateInputUseCase;
import com.task.login.presentation.state.LoginState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private final LoginUseCase loginUseCase;

    private MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ValidateInputUseCase validateInputUseCase = new ValidateInputUseCase();

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
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
                            throwable -> loginState.setValue(new LoginState(false, false, true, "Login failed"))
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
        // TODO: complete this function
        return true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}