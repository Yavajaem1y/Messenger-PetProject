package com.androidlesson.data.authorization;

import androidx.annotation.NonNull;

import com.androidlesson.domain.authorization.authorizationUseCase.AuthorizationError;
import com.androidlesson.domain.authorization.models.LoginData;
import com.androidlesson.domain.authorization.models.RegistrationData;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;
import com.androidlesson.domain.authorization.repository.CallbackAuthorizaiton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthotizationRepositoryImpl implements AuthorizationRepository {

    private FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    public void login(LoginData logData, CallbackAuthorizaiton callback, AuthorizationError authorizationError) {
        auth.signInWithEmailAndPassword(logData.getEmail(),logData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) callback.taskIsSuccessful();
                else {
                    authorizationError.showError("Wrong email or password");
                    callback.taskIsCanceled();
                }
            }
        });
    }

    @Override
    public void registation(RegistrationData regData,CallbackAuthorizaiton callback,AuthorizationError authorizationError) {
        auth.createUserWithEmailAndPassword(regData.getEmail(),regData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) callback.taskIsSuccessful();
                else {
                    authorizationError.showError("An account with such an email already exists");
                    callback.taskIsCanceled();
                }
            }
        });
    }

    @Override
    public boolean checkCurrUser() {
        return auth.getCurrentUser()!=null;
    }
}
