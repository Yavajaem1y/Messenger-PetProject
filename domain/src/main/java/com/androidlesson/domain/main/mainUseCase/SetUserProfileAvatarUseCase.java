package com.androidlesson.domain.main.mainUseCase;

import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;


public class SetUserProfileAvatarUseCase {

    private final MainSharedPreferencesRepository mainSharedPreferencesRepository;
    private final MainFirebaseRepository mainFirebaseRepository;

    public SetUserProfileAvatarUseCase(MainSharedPreferencesRepository mainSharedPreferencesRepository, MainFirebaseRepository mainFirebaseRepository) {
        this.mainSharedPreferencesRepository = mainSharedPreferencesRepository;
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(byte[] image){
        mainFirebaseRepository.setCurrnetUserAvatar(image);
    };
}
