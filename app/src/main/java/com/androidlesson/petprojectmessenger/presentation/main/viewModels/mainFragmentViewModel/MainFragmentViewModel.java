package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.AllUsersFragment;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.ChatsFragment;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.CurrentUserProfileFragment;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableCallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableUserData;

import java.util.HashMap;
import java.util.Map;

public class MainFragmentViewModel extends ViewModel {
    private final Map<Integer,Fragment> fragmentMap=new HashMap<>();
    private UserData userData;

    public MainFragmentViewModel() {
        Log.d("AAA","VM create");
    }

    //Set fragments with info since last activity
    public void setFragmentsInfo(UserData userData,CallbackLogOut callbackLogOut){
        if (fragmentMap.isEmpty() || this.userData!=userData){
            //Creating user profile fragment
            this.userData=userData;
            Bundle bundleForCurrentUserProfileFragment=new Bundle();
            bundleForCurrentUserProfileFragment.putSerializable("USERDATA",new SerializableUserData(this.userData));
            bundleForCurrentUserProfileFragment.putSerializable("CALLBACK_LOG_OUT",new SerializableCallbackLogOut(callbackLogOut));
            Fragment currUserProfileFragment=new CurrentUserProfileFragment();
            currUserProfileFragment.setArguments(bundleForCurrentUserProfileFragment);
            fragmentMap.put(R.id.navigation_current_user_profile,currUserProfileFragment);

            //Creating other models
            fragmentMap.put(R.id.navigation_chats,new ChatsFragment());
            fragmentMap.put(R.id.navigation_all_users,new AllUsersFragment());
            Log.d("AAA","Map is empty");
            mainFragmentSceneMutableLiveData.setValue(fragmentMap.get(R.id.navigation_current_user_profile));
        }
    }

    //The logic of replacing fragments
    private MutableLiveData<Fragment> mainFragmentSceneMutableLiveData=new MutableLiveData<>();
    public LiveData<Fragment> getMainFragmentSceneLiveData(){
        return mainFragmentSceneMutableLiveData;
    }
    public void replaceFragment(int id){
        if (mainFragmentSceneMutableLiveData!=null && mainFragmentSceneMutableLiveData.getValue()!=fragmentMap.get(id)){
            mainFragmentSceneMutableLiveData.setValue(fragmentMap.get(id));
        }
    }
}
