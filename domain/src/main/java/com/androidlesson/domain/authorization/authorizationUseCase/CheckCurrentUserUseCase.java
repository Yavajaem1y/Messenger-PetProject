package com.androidlesson.domain.authorization.authorizationUseCase;


import com.androidlesson.domain.authorization.repository.AuthorizationRepository;

public class CheckCurrentUserUseCase {

    private AuthorizationRepository authRepository;

    public CheckCurrentUserUseCase(AuthorizationRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean execute(){
        return authRepository.checkCurrUser();
    }
}
