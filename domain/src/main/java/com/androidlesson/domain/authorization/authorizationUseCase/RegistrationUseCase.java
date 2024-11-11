package com.androidlesson.domain.authorization.authorizationUseCase;


import com.androidlesson.domain.authorization.Utils.AuthorizationUtils;
import com.androidlesson.domain.authorization.models.AuthorizationErrorsType;
import com.androidlesson.domain.authorization.models.RegistrationData;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;
import com.androidlesson.domain.authorization.repository.CallbackAuthorizaiton;

public class RegistrationUseCase {

    AuthorizationRepository authRepository;
    AuthorizationErrorsType errorsType=new AuthorizationErrorsType();

    public RegistrationUseCase(AuthorizationRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(RegistrationData regData, CallbackAuthorizaiton callback,AuthorizationError authorizationError){
        String email=regData.getEmail();
        String password= regData.getPassword();
        String repassword = regData.getRepassword();
        if (email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            authorizationError.showError(errorsType.getErrorType(0));
            return;
        }
        else if (!password.equals(repassword)) {
            authorizationError.showError(errorsType.getErrorType(1));
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
        authRepository.registation(regData,callback,authorizationError);
    }
}
