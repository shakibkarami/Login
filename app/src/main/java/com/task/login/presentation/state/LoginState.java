package com.task.login.presentation.state;

public class LoginState {
    private boolean isLoading;
    private boolean isSuccess;
    private boolean isError;
    private String errorMessage;
    private String token;

    public LoginState(boolean isLoading, boolean isSuccess, boolean isError, String errorMessage, String token) {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.isError = isError;
        this.errorMessage = errorMessage;
        this.token = token;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getToken() {
        return token;
    }


}