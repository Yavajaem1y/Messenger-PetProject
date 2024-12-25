package com.androidlesson.data.main.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlesson.domain.main.callbacks.CallbackCheckAvailableIds;
import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.callbacks.CallbackWithId;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserInfo;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Observable;

public class MainFirebaseRepositoryImpl implements MainFirebaseRepository {

    private FirebaseDatabase firebaseDatabase;

    private String userSystemId;
    private String userId;


    //Constant values
    private final String DATABASE_WITH_USERS_DATA ="USERS_DATA_DATABASE";
    private final String DATABASE_SYSTEM_ID_TO_APP_ID="DATABASE_SYSTEM_ID_TO_APP_ID";
    private final String USER_NAME="userName";
    private final String USER_ID="userId";
    private final String USER_SURNAME="userSurname";
    private final String USER_FRIENDS_IDS="friendsIds";
    private final String USER_SUBSCRIBERS_IDS="taskToFriendsIds";

    //Initialization FirebaseDatabase
    public MainFirebaseRepositoryImpl() {
        firebaseDatabase =FirebaseDatabase.getInstance();
        userSystemId=FirebaseAuth.getInstance().getUid();
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())userId=snapshot.child(USER_ID).getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Get current user data
    @Override
    public void getUserData(CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userId=snapshot.child(USER_ID).getValue(String.class);
                    Log.d("AAA","id from db "+userId);
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String name = snapshot.child(USER_NAME).getValue(String.class);
                                        String surname = snapshot.child(USER_SURNAME).getValue(String.class);

                                        List<String> friendsIds = new ArrayList<>();
                                        for (DataSnapshot fIds : snapshot.child(USER_FRIENDS_IDS).getChildren()) {
                                            if (fIds.exists()) {
                                                friendsIds.add(fIds.getValue(String.class));
                                            }
                                        }

                                        List<String> subscribersIds = new ArrayList<>();
                                        for (DataSnapshot fIds : snapshot.child(USER_SUBSCRIBERS_IDS).getChildren()) {
                                            if (fIds.exists()) {
                                                subscribersIds.add(fIds.getValue(String.class));
                                            }
                                        }

                                        if (name != null && surname != null) {
                                            callbackGetUserData.getUserData(new UserData(userId, name, surname, friendsIds, subscribersIds));
                                        } else {
                                            callbackGetUserData.getUserData(null);
                                        }
                                    } else {
                                        callbackGetUserData.getUserData(null);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    callbackGetUserData.getUserData(null);
                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void saveUserData(UserData userData, CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).child(USER_ID).setValue(userData.getUserId()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userId=userData.getUserId();
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).setValue(new UserInfo(userData.getUserName(),userData.getUserSurname())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                callbackGetUserData.getUserData(new UserData(userId, userData.getUserName(), userData.getUserSurname()));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void checkAvailableIds(String id, CallbackCheckAvailableIds callbackcheckAvailableIds) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot child:snapshot.getChildren()){
                        String idFromDb=child.child(USER_ID).getValue(String.class);
                        if (id.equals(idFromDb)) callbackcheckAvailableIds.check(true);

                    }
                    callbackcheckAvailableIds.check(false);
                }
                callbackcheckAvailableIds.check(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getBasicId(CallbackWithId callbackWithId) {
        final int[] last_id = new int[1];
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child("LAST_KEY").child(USER_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                last_id[0] =snapshot.getValue(Integer.class)+1;
                callbackWithId.getId(String.valueOf(last_id[0]));
                firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child("LAST_KEY").child(USER_ID).setValue(last_id[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private Set<String> existingKeys = new HashSet<>();

    @Override
    public Observable<List<UserData>> loadAllUser(String lastKey, int limit) {
        return Observable.create(emitter -> {
            Query query;
            if (lastKey == null || lastKey.isEmpty()) {
                query = firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA)
                        .orderByKey().limitToFirst(limit);
            } else {
                query = firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA)
                        .orderByKey().startAfter(lastKey).limitToFirst(limit);
            }

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    List<UserData> users = new ArrayList<>();

                    for (DataSnapshot child : snapshot.getChildren()) {
                        String key = child.getKey();
                        if (!key.equals(userId) && !existingKeys.contains(key)) {
                            existingKeys.add(key);
                            String userName = child.child(USER_NAME).getValue(String.class);
                            String userSurname = child.child(USER_SURNAME).getValue(String.class);

                            List<String> friendsIds = new ArrayList<>();
                            for (DataSnapshot fIds : child.child(USER_FRIENDS_IDS).getChildren()) {
                                if (fIds.exists()) {
                                    friendsIds.add(fIds.getValue(String.class));
                                }
                            }

                            List<String> subscribersIds = new ArrayList<>();
                            for (DataSnapshot fIds : child.child(USER_SUBSCRIBERS_IDS).getChildren()) {
                                if (fIds.exists()) {
                                    subscribersIds.add(fIds.getValue(String.class));
                                }
                            }

                            if (subscribersIds.isEmpty()) {
                                Log.d("friend", "subscribersIds is empty");
                            }
                            else Log.d("friend", "subscribersIds has "+subscribersIds.size());

                            users.add(new UserData(key, userName, userSurname, friendsIds, subscribersIds));
                        }
                    }

                    // Возвращаем результат, когда все данные обработаны
                    emitter.onNext(users);
                    emitter.onComplete();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    emitter.onError(error.toException());
                }
            });
        });
    }

    @Override
    public void addFriend(UserData currUser, UserData anotherUser) {
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(currUser.getUserId()).setValue(new UserInfo(currUser));
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).setValue(new UserInfo(anotherUser));
    }

    @Override
    public void subscribeOnUser(UserData anotherUser) {
        List<String> c=anotherUser.getTaskToFriendsIds();
        for (String i:c){
            Log.d("friend","task to db - "+i);
        }
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).setValue(new UserInfo(anotherUser));
    }
}
