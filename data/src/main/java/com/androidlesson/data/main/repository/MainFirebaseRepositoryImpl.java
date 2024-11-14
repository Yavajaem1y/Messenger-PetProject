package com.androidlesson.data.main.repository;

import androidx.annotation.NonNull;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFirebaseRepositoryImpl implements MainFirebaseRepository {

    private FirebaseDatabase firebaseDatabase;

    private String userID;

    //Constant values
    private final String DATABASE_NAME_WITH_USERS_DATA="USERS_DATA_DATABASE";
    private final String USER_NAME="userName";
    private final String USER_SURNAME="userSurname";

    //Initialization FirebaseDatabase
    public MainFirebaseRepositoryImpl() {
        firebaseDatabase =FirebaseDatabase.getInstance();
        userID= FirebaseAuth.getInstance().getUid();
    }

    //Get current user data
    @Override
    public void getUserData(CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_NAME_WITH_USERS_DATA).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name=snapshot.child(USER_NAME).getValue(String.class);
                    String surname=snapshot.child(USER_SURNAME).getValue(String.class);
                    if (!name.isEmpty() && !surname.isEmpty()){
                        callbackGetUserData.getUserData(new UserData(userID,name,surname));
                    }
                    else callbackGetUserData.getUserData(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void saveUserData(UserNameAndSurname userNameAndSurname, CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_NAME_WITH_USERS_DATA).child(userID).setValue(userNameAndSurname).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callbackGetUserData.getUserData(new UserData(userID,userNameAndSurname.getUserName(),userNameAndSurname.getUserSurname()));
                }
            }
        });
    }
}
