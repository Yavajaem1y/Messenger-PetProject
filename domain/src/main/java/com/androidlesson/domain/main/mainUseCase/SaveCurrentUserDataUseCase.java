package com.androidlesson.domain.main.mainUseCase;

import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;
import com.androidlesson.domain.main.repository.callbacks.CallbackSaveUserData;

public class SaveCurrentUserDataUseCase {
    private MainSharedPreferencesRepository sharedPreferencesRepository;
    private MainFirebaseRepository mainFirebaseRepository;

    public SaveCurrentUserDataUseCase(MainSharedPreferencesRepository sharedPreferencesRepository, MainFirebaseRepository mainFirebaseRepository) {
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(FullUserData fullUserData, CallbackSaveUserData callbackSaveUserData){
        if (fullUserData.getUserID().isEmpty())
            fullUserData.setUserID(mainFirebaseRepository.getCurrentUserId());
        if (fullUserData.getUserName().isEmpty() || fullUserData.getUserSurname().isEmpty()) return;
        else {
            sharedPreferencesRepository.saveUserData(fullUserData,callbackSaveUserData);
            mainFirebaseRepository.setCurrentUserData(fullUserData);
        }
    }
}
