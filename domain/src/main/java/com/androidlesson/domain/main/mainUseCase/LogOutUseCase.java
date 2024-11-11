package com.androidlesson.domain.main.mainUseCase;

import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;

public class LogOutUseCase {
    private MainSharedPreferencesRepository mainSharedPreferencesRepository;
    private MainFirebaseRepository mainFirebaseRepository;

    public LogOutUseCase(MainSharedPreferencesRepository sharedPreferencesRepository, MainFirebaseRepository mainFirebaseRepository) {
        this.mainSharedPreferencesRepository = sharedPreferencesRepository;
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(){
        mainFirebaseRepository.logout();
        mainSharedPreferencesRepository.clearUserData();
    }
}
