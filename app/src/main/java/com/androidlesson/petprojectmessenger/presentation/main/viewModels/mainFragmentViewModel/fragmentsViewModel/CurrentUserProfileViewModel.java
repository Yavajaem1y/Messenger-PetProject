package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel;

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
}
