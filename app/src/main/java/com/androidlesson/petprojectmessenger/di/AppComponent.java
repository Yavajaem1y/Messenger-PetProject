package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.petprojectmessenger.presentation.authorization.AuthorizationActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.EditUserDataActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.MainActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.SetCurrentUserDataFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.AnotherUserProfileActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.ChatWithUserActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments.AllChatsFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments.AllUsersFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments.CurrentUserProfileFragment;

import dagger.Component;

@Component(modules = {AppModule.class, DomainModule.class, DataModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void injectAuthorizationActivity(AuthorizationActivity authorizationActivity);

    void injectMainActivity(MainActivity mainActivity);

    void injectSetCurrentUserDataFragment(SetCurrentUserDataFragment setCurrentUserDataFragment);

    void injectAllUsersFragment(AllUsersFragment allUsersFragment);

    void injectAnotherProfileActivity(AnotherUserProfileActivity anotherUserProfileActivity);

    void injectMainFragment(MainFragment mainFragment);

    void injectCurrentUserProfileFragment(CurrentUserProfileFragment currentUserProfileFragment);

    void injectChatWithUserActivity(ChatWithUserActivity chatWithUserActivity);

    void injectAllChatsFragment(AllChatsFragment allChatsFragment);

    void injectEditUserDataActivity(EditUserDataActivity editUserDataActivity);
}
