package com.androidlesson.petprojectmessenger.presentation.main.viewModels.editUserDataActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.OnSuccessCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToEdit;
import com.androidlesson.domain.main.usecase.EditUserDataUseCase;

public class EditUserDataActivityViewModel extends ViewModel {
    private EditUserDataUseCase editUserDataUseCase;
    private MutableLiveData<Boolean> finishMutableLiveData=new MutableLiveData<>();

    public EditUserDataActivityViewModel(EditUserDataUseCase editUserDataUseCase) {
        this.editUserDataUseCase = editUserDataUseCase;
    }

    private UserData userData;

    public void saveUserData(UserData userData){
        this.userData=userData;
    }

    public LiveData<Boolean> getFinishLiveData(){return finishMutableLiveData;};

    public void editData(UserDataToEdit userDataToEdit){
        if (userData!=null){
            editUserDataUseCase.execute(userData.getUserId(), userDataToEdit, new OnSuccessCallback() {
                @Override
                public void Success(Boolean bool) {
                    finishMutableLiveData.setValue(bool);
                }
            });
        }
    }
}
