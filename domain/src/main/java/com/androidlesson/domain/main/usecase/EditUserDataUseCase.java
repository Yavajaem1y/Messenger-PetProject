package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.OnSuccessCallback;
import com.androidlesson.domain.main.models.UserDataToEdit;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class EditUserDataUseCase {
    private MainFirebaseRepository mainFirebaseRepository;

    public EditUserDataUseCase(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(String userId,UserDataToEdit userDataToEdit, OnSuccessCallback onSuccessCallback){
        if (userDataToEdit!=null && onSuccessCallback!=null){
            mainFirebaseRepository.editUserData(userId,userDataToEdit,onSuccessCallback);
        }
    }
}
