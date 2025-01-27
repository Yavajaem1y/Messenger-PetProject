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

import java.security.PublicKey;
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
    private final String USER_TASK_TO_FRIEND="taskToFriendsIds";
    private final String USER_SUBSCRIBERS_IDS="subscribersIds";
    private final String USER_CHATS_IDS="chatIds";

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
    public void getCurrentUserData(CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userId = snapshot.child(USER_ID).getValue(String.class);
                    getUserDataById(userId,callbackGetUserData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getUserDataById(String id, CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String name = snapshot.child(USER_NAME).getValue(String.class);
                            String surname = snapshot.child(USER_SURNAME).getValue(String.class);

                            // Проверка на null для name и surname
                            if (name != null && surname != null) {
                                List<String> friendsIds = new ArrayList<>();
                                for (DataSnapshot friendSnapshot : snapshot.child(USER_FRIENDS_IDS).getChildren()) {
                                    String friendId = friendSnapshot.getValue(String.class);
                                    if (friendId != null) {
                                        friendsIds.add(friendId);
                                    }
                                }

                                List<String> subscribersIds = new ArrayList<>();
                                for (DataSnapshot subscriberSnapshot : snapshot.child(USER_SUBSCRIBERS_IDS).getChildren()) {
                                    String subscriberId = subscriberSnapshot.getValue(String.class);
                                    if (subscriberId != null) {
                                        subscribersIds.add(subscriberId);
                                    }
                                }

                                List<String> taskToFriendsIds = new ArrayList<>();
                                for (DataSnapshot taskSnapshot : snapshot.child(USER_TASK_TO_FRIEND).getChildren()) {
                                    String taskId = taskSnapshot.getValue(String.class);
                                    if (taskId != null) {
                                        taskToFriendsIds.add(taskId);
                                    }
                                }

                                List<String> chatsIds = new ArrayList<>();
                                for (DataSnapshot chatSnapshot : snapshot.child(USER_CHATS_IDS).getChildren()) {
                                    String chatId = chatSnapshot.getValue(String.class);
                                    if (chatId != null) {
                                        chatsIds.add(chatId);
                                    }
                                }

                                // Возвращаем данные через callback
                                callbackGetUserData.getUserData(new UserData(id, name, surname, friendsIds, taskToFriendsIds, subscribersIds, chatsIds));
                            } else {
                                // Если name или surname отсутствуют, возвращаем null
                                callbackGetUserData.getUserData(null);
                            }
                        } else {
                            // Если данные для пользователя не найдены
                            callbackGetUserData.getUserData(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Логирование ошибки и передача null через callback
                        Log.e("FirebaseError", "Error fetching user data", error.toException());
                        callbackGetUserData.getUserData(null);
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
                        if (!child.getKey().equals("LAST_KEY")) {
                            String idFromDb = child.child(USER_ID).getValue(String.class);
                            if (id.equals(idFromDb)) callbackcheckAvailableIds.check(true);
                        }

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
                    List<String> existingKeys = new ArrayList<>();

                    for (DataSnapshot child : snapshot.getChildren()) {
                        String key = child.getKey();
                        if (!key.equals(userId) && !existingKeys.contains(key)) {
                            existingKeys.add(key);

                            String userName = child.child(USER_NAME).getValue(String.class);
                            String userSurname = child.child(USER_SURNAME).getValue(String.class);

                            users.add(new UserData(key, userName, userSurname));
                        }
                    }

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
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).child(USER_TASK_TO_FRIEND).setValue(anotherUser.getTaskToFriendsIds());
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).child(USER_FRIENDS_IDS).setValue(anotherUser.getFriendsIds());

        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(currUser.getUserId()).child(USER_FRIENDS_IDS).setValue(currUser.getFriendsIds());
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(currUser.getUserId()).child(USER_SUBSCRIBERS_IDS).setValue(currUser.getSubscribersIds());

    }

    @Override
    public void subscribeOnUser(UserData currUser,UserData anotherUser) {
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).child(USER_SUBSCRIBERS_IDS).setValue(anotherUser.getSubscribersIds());
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(currUser.getUserId()).child(USER_TASK_TO_FRIEND).setValue(currUser.getTaskToFriendsIds());
    }

    @Override
    public void observeUserData(CallbackGetUserData callbackGetUserData) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userId = snapshot.child(USER_ID).getValue(String.class);
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserData user=snapshot.getValue(UserData.class);
                            user.setUserId(userId);
                            if (user.getFriendsIds()==null) user.setFriendsIds(new ArrayList<>());
                            if (user.getSubscribersIds()==null) user.setSubscribersIds(new ArrayList<>());
                            if (user.getTaskToFriendsIds()==null) user.setTaskToFriendsIds(new ArrayList<>());
                            if (user.getChatIds()==null) user.setChatIds(new ArrayList<>());
                            if (user!=null) {
                                callbackGetUserData.getUserData(user);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
