package com.androidlesson.data.main.repositoryForUseCase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.repository.callbacks.CallbackSaveUserData;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;

public class MainSharedPreferencesRepositoryImpl implements MainSharedPreferencesRepository {

    private SharedPreferences sharedPreferences;

    private final String SPREF_NAME="CURRENT_USER_SPREF";
    private final String CURRENT_USER_ID="CURRENT_USER_ID";
    private final String CURRENT_USER_NAME="CURRENT_USER_NAME";
    private final String CURRENT_USER_SURNAME="CURRENT_USER_SURNAME";

    public MainSharedPreferencesRepositoryImpl(Context context) {
        sharedPreferences= context.getSharedPreferences(SPREF_NAME,Context.MODE_PRIVATE);
    }


    @Override
    public FullUserData loadUserData() {
        String id=sharedPreferences.getString(CURRENT_USER_ID,"");
        String name=sharedPreferences.getString(CURRENT_USER_NAME,"");
        String surname= sharedPreferences.getString(CURRENT_USER_SURNAME,"");

        return new FullUserData(id,name,surname);
    }

    @Override
    public UserNameAndSurname getUserNameAndSurname() {
        String name=sharedPreferences.getString(CURRENT_USER_NAME,"");
        String surname= sharedPreferences.getString(CURRENT_USER_SURNAME,"");
        return new UserNameAndSurname(name,surname);
    }

    @Override
    public void refreshCurrentUserNameAndSurname(UserNameAndSurname user) {
        sharedPreferences.edit().putString(CURRENT_USER_NAME, user.getUserName()).apply();
        sharedPreferences.edit().putString(CURRENT_USER_SURNAME, user.getUserSurname()).apply();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void saveUserData(FullUserData fullUserData, CallbackSaveUserData callbackSaveUserData) {
        sharedPreferences.edit().putString(CURRENT_USER_ID, fullUserData.getUserID()).apply();
        sharedPreferences.edit().putString(CURRENT_USER_NAME, fullUserData.getUserName()).apply();
        sharedPreferences.edit().putString(CURRENT_USER_SURNAME, fullUserData.getUserSurname()).apply();
        callbackSaveUserData.checkUserDataSave();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void clearUserData() {
        sharedPreferences.edit().clear().apply();
    }

}
