package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;

public class CurrentUserProfileViewModel extends ViewModel {

    public CurrentUserProfileViewModel(UserData userData) {
        if(userData!=null) userDataMutableLiveData.setValue(userData);
    }

    private MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getUserDataLiveData(){
        return userDataMutableLiveData;
    }
}
