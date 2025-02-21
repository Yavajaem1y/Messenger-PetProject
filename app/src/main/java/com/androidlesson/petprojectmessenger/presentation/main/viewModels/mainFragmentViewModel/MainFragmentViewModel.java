package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments.AllUsersFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments.AllChatsFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments.CurrentUserProfileFragment;

import java.util.HashMap;
import java.util.Map;

public class MainFragmentViewModel extends ViewModel {
    private final Map<Integer,Fragment> fragmentMap=new HashMap<>();
    private UserData userData;

    public MainFragmentViewModel() {
    }

    //Set fragments with info since last activity
    public void setFragmentsInfo(UserData userData){
        if (fragmentMap.isEmpty() || this.userData!=userData){

            this.userData=userData;
            Bundle bundleForCurrentUserProfileFragment=new Bundle();
            bundleForCurrentUserProfileFragment.putSerializable("USERDATA",userData);

            //Creating user profile fragment
            Fragment currUserProfileFragment=new CurrentUserProfileFragment();
            currUserProfileFragment.setArguments(bundleForCurrentUserProfileFragment);
            fragmentMap.put(R.id.navigation_current_user_profile,currUserProfileFragment);

            //Creating all users fragment
            Fragment allUsersFragment= new AllUsersFragment();
            allUsersFragment.setArguments(bundleForCurrentUserProfileFragment);
            fragmentMap.put(R.id.navigation_all_users,allUsersFragment);

            //Creating other models
            fragmentMap.put(R.id.navigation_chats,new AllChatsFragment());
            mainFragmentSceneMutableLiveData.setValue(fragmentMap.get(R.id.navigation_current_user_profile));
        }
    }

    //The logic of replacing fragments
    private MutableLiveData<Fragment> mainFragmentSceneMutableLiveData=new MutableLiveData<>();
    public LiveData<Fragment> getMainFragmentSceneLiveData(){
        return mainFragmentSceneMutableLiveData;
    }

    private MutableLiveData<Integer> selectedItemIdBNVMutableLiveData=new MutableLiveData<>();
    public LiveData<Integer> getSelectedItemIdBNVLiveData(){
        return selectedItemIdBNVMutableLiveData;
    }

    public void replaceFragment(int id){
        selectedItemIdBNVMutableLiveData.setValue(id);
        if (mainFragmentSceneMutableLiveData!=null && mainFragmentSceneMutableLiveData.getValue()!=fragmentMap.get(id)){
            mainFragmentSceneMutableLiveData.setValue(fragmentMap.get(id));
        }
    }

    public void setUserData(UserData userData){
        this.userData=userData;
    }
}
