package com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.interfaces.OnImageUrlFetchedListener;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.ObserveCurrentUserDataUseCase;
import com.androidlesson.domain.main.usecase.UserAvatarImageListener;

public class SharedViewModel extends ViewModel {

    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;
    private final UserAvatarImageListener userAvatarImageListener;
    private final MutableLiveData<UserData> userDataLiveData = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> firstFragmentLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<String> currentUserAvatarLivaData = new MutableLiveData<>();


    public SharedViewModel(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase,UserAvatarImageListener userAvatarImageListener) {
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;
        this.userAvatarImageListener=userAvatarImageListener;

        loadUserData();

    }

    private void loadUserData() {
        observeCurrentUserDataUseCase.execute(new CallbackGetUserData() {
            @Override
            public void getUserData(UserData userData) {
                if (userData != null) {
                    Log.d("SharedViewModel", "Loaded user: " + userData.getUserName());
                    userDataLiveData.postValue(userData);
                    avatarChangeLister(userData.getUserId());
                } else {
                    Log.d("SharedViewModel", "UserData is null");
                    userDataLiveData.postValue(null);
                }
            }
        });
    }

    private void avatarChangeLister(String userId) {
        userAvatarImageListener.execute(userId, new OnImageUrlFetchedListener() {
            @Override
            public void onSuccess(String imageUri) {
                if (imageUri != null && !imageUri.isEmpty()) {
                    currentUserAvatarLivaData.setValue(imageUri);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("SharedViewModel", "Error loading avatar", e);
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
    public LiveData<String> getCurrentUserAvatarLiveData() {
        return currentUserAvatarLivaData;
    }



    @Override
    protected void onCleared() {
        Log.d("SharedViewModel","SharedViewModel is cleared");
        super.onCleared();
    }
}
