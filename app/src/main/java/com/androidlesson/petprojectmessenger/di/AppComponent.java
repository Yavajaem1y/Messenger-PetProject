package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.petprojectmessenger.presentation.authorization.AuthorizationActivity;
import com.androidlesson.petprojectmessenger.presentation.main.MainActivity;
import com.androidlesson.petprojectmessenger.presentation.main.SetCurrentUserDataFragment;
import com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.anotherActivity.AnotherUserProfileActivity;
import com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.fragments.AllUsersFragment;

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
}
