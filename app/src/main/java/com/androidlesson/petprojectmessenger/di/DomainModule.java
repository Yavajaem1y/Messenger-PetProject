package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;
import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;
import com.androidlesson.domain.main.usecase.DeleteAFriendUseCase;
import com.androidlesson.domain.main.usecase.EditUserDataUseCase;
import com.androidlesson.domain.main.usecase.GoToChatViewUseCase;
import com.androidlesson.domain.main.usecase.LoadAllChatsUseCase;
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
import com.androidlesson.domain.main.usecase.UploadImageAvatarUseCase;
import com.androidlesson.domain.main.usecase.UserAvatarImageListener;

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
    public LoadOldMessageUseCase provideLoadAllMessageUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadOldMessageUseCase(mainFirebaseRepository);
    }

    @Provides
    public LoadChatInfoUseCase provideLoadChatInfoUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadChatInfoUseCase(mainFirebaseRepository);
    }

    @Provides
    public LoadNewMessageUseCase provideLoadNewMessageUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadNewMessageUseCase(mainFirebaseRepository);
    }

    @Provides
    public LoadAllChatsUseCase provideLoadAllChatsUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new LoadAllChatsUseCase(mainFirebaseRepository);
    }

    @Provides
    public UploadImageAvatarUseCase provideUploadImageAvatarUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new UploadImageAvatarUseCase(mainFirebaseRepository);
    }

    @Provides
    public UserAvatarImageListener provideUserAvatarImageListener(MainFirebaseRepository mainFirebaseRepository){
        return new UserAvatarImageListener(mainFirebaseRepository);
    }

    @Provides
    public DeleteAFriendUseCase provideDeleteAFriendUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new DeleteAFriendUseCase(mainFirebaseRepository);
    }

    @Provides
    public EditUserDataUseCase provideEditUserDataUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new EditUserDataUseCase(mainFirebaseRepository);
    }
}
