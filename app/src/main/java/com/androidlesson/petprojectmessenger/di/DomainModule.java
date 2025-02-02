package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;
import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;
import com.androidlesson.domain.main.usecase.GoToChatViewUseCase;
import com.androidlesson.domain.main.usecase.LoadAllMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadAllUserUseCase;
import com.androidlesson.domain.main.usecase.LoadChatInfoUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataUseCase;
import com.androidlesson.domain.main.usecase.LogOutUseCase;
import com.androidlesson.domain.main.usecase.ObserveCurrentUserDataUseCase;
import com.androidlesson.domain.main.usecase.SaveUserDataUseCase;
import com.androidlesson.domain.main.usecase.SendAMessageUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    //Authorization UseCases
    @Provides
    public LoginUseCase provideLoginUseCase(AuthorizationRepository authorizationRepository){
        return new LoginUseCase(authorizationRepository);
    }

    @Provides
    public CheckCurrentUserUseCase provideCheckCurrentUserUseCase(AuthorizationRepository authorizationRepository){
        return new CheckCurrentUserUseCase(authorizationRepository);
    }

    @Provides
    public RegistrationUseCase provideRegistrationUseCase(AuthorizationRepository authorizationRepository){
        return new RegistrationUseCase(authorizationRepository);
    }

    //Main UseCases
    @Provides
    public LoadUserDataUseCase provideLoadUserDataUseCase(MainFirebaseRepository firebaseRepository, MainSharedPrefRepository sharedPrefRepository){
        return new LoadUserDataUseCase(firebaseRepository,sharedPrefRepository);
    }

    @Provides
    public LoadUserDataByIdUseCase provideLoadUserDataByIdUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadUserDataByIdUseCase(mainFirebaseRepository);
    }

    @Provides
    public LogOutUseCase provideLogOutUseCase(MainFirebaseRepository firebaseRepository, MainSharedPrefRepository sharedPrefRepository){
        return new LogOutUseCase(firebaseRepository,sharedPrefRepository);
    }

    @Provides
    public SaveUserDataUseCase provideSaveUserDataUseCase(MainFirebaseRepository firebaseRepository, MainSharedPrefRepository sharedPrefRepository){
        return new SaveUserDataUseCase(firebaseRepository,sharedPrefRepository);
    }

    @Provides
    public LoadAllUserUseCase provideLoadAllUserUseCase(MainFirebaseRepository firebaseRepository){
        return new LoadAllUserUseCase(firebaseRepository);
    }

    @Provides
    public AddToFriendsUseCase provideAddToFriendsUseCase(MainFirebaseRepository firebaseRepository){
        return new AddToFriendsUseCase(firebaseRepository);
    }

    @Provides
    public ObserveCurrentUserDataUseCase provideObserveCurrentUserDataUseCase(MainFirebaseRepository firebaseRepository){
        return new ObserveCurrentUserDataUseCase(firebaseRepository);
    }

    @Provides
    public GoToChatViewUseCase provideGoToChatViewUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new GoToChatViewUseCase(mainFirebaseRepository);
    }

    @Provides
    public SendAMessageUseCase provideSendAMessageUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new SendAMessageUseCase(mainFirebaseRepository);
    }

    @Provides
    public LoadAllMessageUseCase provideLoadAllMessageUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadAllMessageUseCase(mainFirebaseRepository);
    }

    @Provides
    public LoadChatInfoUseCase provideLoadChatInfoUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadChatInfoUseCase(mainFirebaseRepository);
    }
}
