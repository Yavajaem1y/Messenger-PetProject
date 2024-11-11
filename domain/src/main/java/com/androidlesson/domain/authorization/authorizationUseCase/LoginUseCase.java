package com.androidlesson.domain.authorization.authorizationUseCase;


import com.androidlesson.domain.authorization.Utils.AuthorizationUtils;
import com.androidlesson.domain.authorization.models.AuthorizationErrorsType;
import com.androidlesson.domain.authorization.models.LoginData;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;
import com.androidlesson.domain.authorization.repository.CallbackAuthorizaiton;

public class LoginUseCase {

    AuthorizationRepository authRepository;
    AuthorizationErrorsType errorsType=new AuthorizationErrorsType();

    public LoginUseCase(AuthorizationRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(LoginData loginData, CallbackAuthorizaiton callback,AuthorizationError authorizationError){
        String email=loginData.getEmail();
        String password= loginData.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            authorizationError.showError(errorsType.getErrorType(0));
            return;
        }
        else if (password.length()<=5) {
            authorizationError.showError(errorsType.getErrorType(2));
            return;
        }
        else if (!AuthorizationUtils.isValidEmail(email)) {
            authorizationError.showError(errorsType.getErrorType(3));
            return;
        }
        authRepository.login(loginData,callback,authorizationError);
    }

}
