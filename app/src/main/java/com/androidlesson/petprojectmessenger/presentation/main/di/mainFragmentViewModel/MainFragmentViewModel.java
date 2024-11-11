package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel;

import android.net.ConnectivityManager;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserAvatarFromMainVM;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.AllUsersFragment;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.ChatsFragment;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.CurrentUserProfileFragment;
import com.androidlesson.domain.main.models.FullUserData;

import java.util.HashMap;
import java.util.Map;

public class MainFragmentViewModel extends ViewModel {
    private CurrentUserDataFromDB userDataFromActivity;

    public MutableLiveData<Fragment> selectedFragmentLiveData=new MutableLiveData<>();
    private FullUserData fullUserData;
    private final MutableLiveData<byte[]> currentUserProfileAvatarImageLiveData=new MutableLiveData<>();

    private final Map<Integer,Fragment> fragmentMap=new HashMap<>();

    public MainFragmentViewModel(CurrentUserDataFromDB currentUserDataFromDB) {
        selectedFragmentLiveData.setValue(new CurrentUserProfileFragment(currentUserAvatarFromMainVM, currentUserDataFromDB));
        fragmentMap.put(R.id.navigation_current_user_profile,new CurrentUserProfileFragment(currentUserAvatarFromMainVM, currentUserDataFromDB));
        fragmentMap.put(R.id.navigation_chats,new ChatsFragment());
        fragmentMap.put(R.id.navigation_all_users,new AllUsersFragment());
        userDataFromActivity=currentUserDataFromDB;
        fullUserData=userDataFromActivity.getCurrentUserData();

    }

    public void selectFragment(Integer id){
        selectedFragmentLiveData.setValue(fragmentMap.get(id));
    }

    private CurrentUserAvatarFromMainVM currentUserAvatarFromMainVM =new CurrentUserAvatarFromMainVM() {
        @Override
        public void setUserAvatar(byte[] image) {
            currentUserProfileAvatarImageLiveData.setValue(image);
        }

        @Override
        public byte[] getUserAvatar() {
            return currentUserProfileAvatarImageLiveData.getValue();
        }
    };

    private CurrentUserDataFromDB currentUserDataFromDB =new CurrentUserDataFromDB() {
        @Override
        public void setCurrentUserData(FullUserData userData) {
            fullUserData=userData;
        }

        @Override
        public FullUserData getCurrentUserData() {
            return fullUserData;
        }
    };
}
