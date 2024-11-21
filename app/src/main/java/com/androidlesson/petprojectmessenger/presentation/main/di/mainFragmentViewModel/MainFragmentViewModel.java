package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel;

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
import java.util.HashMap;
import java.util.Map;

public class MainFragmentViewModel extends ViewModel {
    private final Map<Integer,Fragment> fragmentMap=new HashMap<>();
    private UserData userData;

    public MainFragmentViewModel(UserData userData, CallbackLogOut callbackLogOut) {
        if (userData!=null) this.userData=userData;
        fragmentMap.put(R.id.navigation_current_user_profile,new CurrentUserProfileFragment(userData,callbackLogOut));
        fragmentMap.put(R.id.navigation_chats,new ChatsFragment());
        fragmentMap.put(R.id.navigation_all_users,new AllUsersFragment());
        mainFragmentSceneMutableLiveData.setValue(fragmentMap.get(R.id.navigation_current_user_profile));
    }

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
