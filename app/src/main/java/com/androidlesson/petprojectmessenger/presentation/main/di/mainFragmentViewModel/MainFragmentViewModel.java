package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.AllUsersFragment;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.ChatsFragment;
import com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar.CurrentUserProfileFragment;
import java.util.HashMap;
import java.util.Map;

public class MainFragmentViewModel extends ViewModel {
    private final Map<Integer,Fragment> fragmentMap=new HashMap<>();

    public MainFragmentViewModel() {
        fragmentMap.put(R.id.navigation_current_user_profile,new CurrentUserProfileFragment());
        fragmentMap.put(R.id.navigation_chats,new ChatsFragment());
        fragmentMap.put(R.id.navigation_all_users,new AllUsersFragment());

    }

}
