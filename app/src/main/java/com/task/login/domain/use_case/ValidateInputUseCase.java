package com.task.login.domain.use_case;

import com.task.login.domain.model.InputValidationType;

public class ValidateInputUseCase {

    public InputValidationType invoke(String username, String password){

        if (username.isEmpty() || password.isEmpty()) return InputValidationType.EmptyInput;

        if (password.length() < 8) return InputValidationType.ShortPassword;

        return InputValidationType.Valid;
    }

}
