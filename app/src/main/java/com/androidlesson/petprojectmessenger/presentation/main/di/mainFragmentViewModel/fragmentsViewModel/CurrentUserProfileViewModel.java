package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentUserProfileViewModel extends ViewModel {
    public MutableLiveData<byte[]> userAvatarMutableLiveData=new MutableLiveData<>();

    public CurrentUserProfileViewModel() {
    }
}
