package com.androidlesson.petprojectmessenger.presentation.main.viewModels.CurrentUserProfileFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.UploadImageAvatarUseCase;

public class CurrentUserProfileViewModel extends ViewModel {

    private UploadImageAvatarUseCase uploadImageAvatarUseCase;

    public CurrentUserProfileViewModel(UploadImageAvatarUseCase uploadImageAvatarUseCase) {
        this.uploadImageAvatarUseCase = uploadImageAvatarUseCase;
    }

    public void setVMInfo(UserData userData){
        if (userData!=null) userDataMutableLiveData.setValue(userData);
    }

    private MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getUserDataLiveData(){
        return userDataMutableLiveData;
    }

    private MutableLiveData<Boolean> visibilityTopElementMutableLiveData=new MutableLiveData<>(false);
    public LiveData<Boolean> getVisibilityTopElementLiveData(){return visibilityTopElementMutableLiveData;};

    public void setVisibilityTopElement(int scaleY){
        if (scaleY>0 && Boolean.FALSE.equals(visibilityTopElementMutableLiveData.getValue())) visibilityTopElementMutableLiveData.setValue(true);
        else if (scaleY==0 && Boolean.TRUE.equals(visibilityTopElementMutableLiveData.getValue())) visibilityTopElementMutableLiveData.setValue(false);
    }

    public void uploadImageAvatar(byte[] imageData){
        if (userDataMutableLiveData.getValue()!=null) {
            uploadImageAvatarUseCase.execute(imageData, userDataMutableLiveData.getValue());
        }
    }

}
