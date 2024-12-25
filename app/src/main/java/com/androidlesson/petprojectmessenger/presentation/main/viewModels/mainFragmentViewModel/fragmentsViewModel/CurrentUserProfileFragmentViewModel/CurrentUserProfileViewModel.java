package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileFragmentViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;

public class CurrentUserProfileViewModel extends ViewModel {

    public CurrentUserProfileViewModel() {
        Log.d("AAA","Profile VM create");
    }

    public void setVMInfo(UserData userData){
        userDataMutableLiveData.setValue(userData);
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

}
