package com.androidlesson.petprojectmessenger.di;

import android.content.Context;

import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;
import com.androidlesson.domain.main.usecase.GoToChatViewUseCase;
import com.androidlesson.domain.main.usecase.LoadNewMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadOldMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadAllUserUseCase;
import com.androidlesson.domain.main.usecase.LoadChatInfoUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataUseCase;
import com.androidlesson.domain.main.usecase.LogOutUseCase;
import com.androidlesson.domain.main.usecase.ObserveCurrentUserDataUseCase;
import com.androidlesson.domain.main.usecase.SaveUserDataUseCase;
import com.androidlesson.domain.main.usecase.SendAMessageUseCase;
import com.androidlesson.petprojectmessenger.presentation.authorization.AuthorizationViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel.AnotherUserProfileActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel.MainActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.AllUsersFragmetViewModel.AllUsersFragmentViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.MainFragmentViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.setCurrentUserDataFragmentViewModel.SetCurrentUserDataFragmentVMFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    //Get context
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    //SharedViewModelFactory
    @Provides
    public SharedViewModelFactory provideSharedViewModelFactory(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase){
        return new SharedViewModelFactory(observeCurrentUserDataUseCase);
    }

    //AuthorizationViewModelFactory
    @Provides
    public AuthorizationViewModelFactory provideAuthorizationViewModelFactory(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, CheckCurrentUserUseCase checkCurrentUserUseCase){
        return new AuthorizationViewModelFactory(loginUseCase,registrationUseCase,checkCurrentUserUseCase);
    }

    //MainActivityViewModelFactory
    @Provides
    public MainActivityViewModelFactory provideMainActivityViewModelFactory(LoadUserDataUseCase loadUserDataUseCase, LogOutUseCase logOutUseCase){
        return new MainActivityViewModelFactory(loadUserDataUseCase,logOutUseCase);
    }

    //SetCurrentUserDataFragmentViewModelFactory
    @Provides
    public SetCurrentUserDataFragmentVMFactory provideSetCurrentUserDataFragmentVMFactory(SaveUserDataUseCase saveUserDataUseCase) {
        return new SetCurrentUserDataFragmentVMFactory(saveUserDataUseCase);
    }

    //AllUsersFragmentViewModelFactory
    @Provides
    public AllUsersFragmentViewModelFactory provideAllUsersFragmentViewModelFactory(LoadAllUserUseCase loadAllUserUseCase) {
        return new AllUsersFragmentViewModelFactory(loadAllUserUseCase);
    }

    //AnotherUserProfileActivity
    @Provides
    public AnotherUserProfileActivityViewModelFactory provideAnotherUserProfileActivityViewModelFactory(AddToFriendsUseCase addToFriendsUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, GoToChatViewUseCase goToChatViewUseCase) {
        return new AnotherUserProfileActivityViewModelFactory(addToFriendsUseCase,loadUserDataByIdUseCase,goToChatViewUseCase);
    }

    @Provides
    public MainFragmentViewModelFactory provideMainFragmentViewModelFactory() {
        return new MainFragmentViewModelFactory();
    }

    @Provides
    public ChatWithUserActivityViewModelFactory provideChatWithUserActivityViewModelFactory(SendAMessageUseCase sendAMessageUseCase, LoadOldMessageUseCase loadOldMessageUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, LoadChatInfoUseCase loadChatInfoUseCase, LoadNewMessageUseCase loadNewMessageUseCase){
        return new ChatWithUserActivityViewModelFactory(sendAMessageUseCase, loadOldMessageUseCase,loadUserDataByIdUseCase,loadChatInfoUseCase,loadNewMessageUseCase);
    }
}
