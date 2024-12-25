package com.androidlesson.data.main.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;

public class MainSharedPrefRepositoryImpl implements MainSharedPrefRepository {

    private Context context;
    private SharedPreferences spDataBase;

    //Constant values
    private final String SP_DATABASE_NAME="SP_DATABASE_NAME";
    private final String USER_ID_FROM_SP="USER_ID_FROM_SP";
    private final String USER_NAME_FROM_SP="USER_NAME_FROM_SP";
    private final String USER_SURNAME_FROM_SP="USER_SURNAME_FROM_SP";

    //Initialization SharedPreferences
    public MainSharedPrefRepositoryImpl(Context context) {
        this.context = context;
        spDataBase=context.getSharedPreferences(SP_DATABASE_NAME,Context.MODE_PRIVATE);
    }

    //Get current user data
    @Override
    public UserData getUserData() {
        String id=spDataBase.getString(USER_ID_FROM_SP,"");
        String name=spDataBase.getString(USER_NAME_FROM_SP,"");
        String surname=spDataBase.getString(USER_SURNAME_FROM_SP,"");
        return new UserData(id,name,surname);
    }

    @Override
    public void saveUserData(UserData userData) {
        SharedPreferences.Editor editor=spDataBase.edit();
        editor.putString(USER_ID_FROM_SP,userData.getUserId()).apply();
        editor.putString(USER_NAME_FROM_SP,userData.getUserName()).apply();
        editor.putString(USER_SURNAME_FROM_SP,userData.getUserSurname()).apply();
    }

    @Override
    public void logOut() {
        SharedPreferences.Editor editor=spDataBase.edit();
        editor.clear().apply();
    }
}
