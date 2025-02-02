package com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.ObserveCurrentUserDataUseCase;

public class SharedViewModel extends ViewModel {

    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;
    private final MutableLiveData<UserData> userDataLiveData = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> firstFragmentLiveData = new MutableLiveData<>(false);


    public SharedViewModel(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase) {
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;

        loadUserData();
    }

    private void loadUserData() {
        observeCurrentUserDataUseCase.execute(new CallbackGetUserData() {
            @Override
            public void getUserData(UserData userData) {
                if (userData != null) {
                    Log.d("SharedViewModel", "Loaded user: " + userData.getUserName() + ", friends count: " +
                            (userData.getFriendsIds() != null ? userData.getFriendsIds().size() : 0));
                    userDataLiveData.postValue(userData);
                } else {
                    Log.d("SharedViewModel", "UserData is null");
                    userDataLiveData.postValue(null);
                }
            }
        });
    }

    public void setFirstFragment(boolean bool){
        firstFragmentLiveData.setValue(bool);
    }

    public LiveData<UserData> getUserData() {
        return userDataLiveData;
    }
    public LiveData<Boolean> getFirstFragment() {
        return firstFragmentLiveData;
    }

}
