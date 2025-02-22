package com.androidlesson.data.main.repository;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidlesson.domain.main.interfaces.OnImageUrlFetchedListener;
import com.androidlesson.domain.main.models.ChatInfoForLoad;
import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.utils.CurrentTimeAndDate;
import com.androidlesson.domain.main.interfaces.CallbackCheckAvailableIds;
import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.interfaces.CallbackWithChatInfo;
import com.androidlesson.domain.main.interfaces.CallbackWithId;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserInfo;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    private final String DATABASE_CHATS_DATA="DATABASE_CHATS_DATA";

    private final String USER_NAME="userName";
    private final String USER_ID="userId";
    private final String USER_SYSTEM_ID="userSystemId";
    private final String USER_SURNAME="userSurname";
    private final String USER_FRIENDS_IDS="friendsIds";
    private final String USER_AVATAR_IMAGE="imageData";
    private final String USER_TASK_TO_FRIEND="taskToFriendsIds";
    private final String USER_SUBSCRIBERS_IDS="subscribersIds";
    private final String USER_CHATS_IDS="chatIds";
    private final String CHAT_FIRST_USER="firstUser";
    private final String CHAT_SECOND_USER="secondUser";
    private final String CHAT_NUMBER_OF_MESSAGES="numberOfMessages";
    private final String CHAT_TIME_LAST_MESSAGE="timeLastMessage";
    private final String CHAT_MESSAGES="messages";
    private final String CHAT_TEXT_LAST_MESSAGE="textLastMessage";

    private static final int PAGE_SIZE = 20;

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
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String systemId= snapshot.child(USER_SYSTEM_ID).getValue(String.class);
                            String name = snapshot.child(USER_NAME).getValue(String.class);
                            String surname = snapshot.child(USER_SURNAME).getValue(String.class);
                            String imageData=snapshot.child(USER_AVATAR_IMAGE).getValue(String.class);

                            if (name != null && surname != null && systemId!=null) {
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

                                if (imageData==null) imageData="";

                                // Возвращаем данные через callback
                                callbackGetUserData.getUserData(new UserData(id,systemId, name, surname, imageData, friendsIds, taskToFriendsIds, subscribersIds, chatsIds));
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
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).setValue(new UserInfo(userSystemId,userData.getUserName(),userData.getUserSurname())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                callbackGetUserData.getUserData(new UserData(userId,userSystemId, userData.getUserName(), userData.getUserSurname(),""));
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
                            String userSystemId = child.child(USER_SYSTEM_ID).getValue(String.class);
                            String userSurname = child.child(USER_SURNAME).getValue(String.class);
                            String imageData= child.child(USER_AVATAR_IMAGE).getValue(String.class);
                            if (imageData==null) imageData="";

                            users.add(new UserData(key,userSystemId, userName, userSurname,imageData));
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

    @Override
    public void goToChatView(ChatInfo chatInfo, CallbackWithChatInfo callbackWithChatInfo) {
        Log.d("BBB","chatID = "+chatInfo.getChatId());
        firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatInfo.getChatId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){ callbackWithChatInfo.getChatId(chatInfo.getChatId()); }
                else {
                    chatInfo.setNumberOfMessages(0);
                    chatInfo.setTimeLastMessage(new CurrentTimeAndDate().getCurrentTime());
                    firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatInfo.getChatId()).setValue(chatInfo.getChatInfoToDB());
                    chatInfo.addNewChatId(chatInfo.getChatId());
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(chatInfo.getFirstUser()).child(USER_CHATS_IDS).setValue(chatInfo.getFirstUserChatsIds());
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(chatInfo.getSecondUser()).child(USER_CHATS_IDS).setValue(chatInfo.getSecondUserChatsIds());
                    callbackWithChatInfo.getChatId(chatInfo.getChatId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getChatInfoById(String chatId, CallbackWithChatInfo callbackWithChatInfo) {
        firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String firstUser=snapshot.child(CHAT_FIRST_USER).getValue(String.class);
                    String secondUser=snapshot.child(CHAT_SECOND_USER).getValue(String.class);
                    String timeLastMessage=snapshot.child(CHAT_TIME_LAST_MESSAGE).getValue(String.class);
                    Integer numberOfMessage=snapshot.child(CHAT_NUMBER_OF_MESSAGES).getValue(Integer.class);
                    ChatInfo chatInfo=new ChatInfo(chatId,firstUser,secondUser,timeLastMessage,numberOfMessage,null,null);
                    callbackWithChatInfo.getChatInfo(chatInfo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void sendAMessageUseCase(ChatInfo.Message message,ChatInfo chatInfo) {
        firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatInfo.getChatId()).child(CHAT_MESSAGES).push().setValue(message);
        firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatInfo.getChatId()).child(CHAT_NUMBER_OF_MESSAGES).setValue(chatInfo.getNumberOfMessages());
        firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatInfo.getChatId()).child(CHAT_TIME_LAST_MESSAGE).setValue(chatInfo.getTimeLastMessage());
        firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatInfo.getChatId()).child(CHAT_TEXT_LAST_MESSAGE).setValue(message.getMessage());
    }

    List<String> messagesId=new ArrayList<>();
    List<ChatInfo.Message> messages = new ArrayList<>();

    @Override
    public Observable<List<ChatInfo.Message>> loadOldMessages(String lastMessageTimestamp, String chatId) {
        DatabaseReference chatRef = firebaseDatabase.getReference(DATABASE_CHATS_DATA).child(chatId).child(CHAT_MESSAGES);
        return Observable.create(emitter -> {
            Query query;
            if (lastMessageTimestamp == null) {
                query = chatRef.orderByChild("timeSending").limitToLast(PAGE_SIZE);
            } else {

                query = chatRef.orderByChild("timeSending").endAt(lastMessageTimestamp).limitToLast(PAGE_SIZE);
            }

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!messagesId.contains(snapshot.getKey())) {
                            messagesId.add(snapshot.getKey());
                            ChatInfo.Message message = snapshot.getValue(ChatInfo.Message.class);
                            if (!messages.contains(message)) {
                                messages.add(message);
                            }
                        }
                    }

                    emitter.onNext(messages);
                    emitter.onComplete();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            });
        });
    }

    @Override
    public void loadNewMessage(String chatId, CallbackWithChatInfo callbackWithChatInfo) {
        firebaseDatabase.getReference(DATABASE_CHATS_DATA)
                .child(chatId)
                .child(CHAT_MESSAGES)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists()) {
                            // Получаем только новое сообщение
                            ChatInfo.Message newMessage = snapshot.getValue(ChatInfo.Message.class);
                            if (newMessage != null) {
                                // Отправляем новое сообщение в callback
                                callbackWithChatInfo.getMessage(newMessage);
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Этот метод сработает, если сообщение будет обновлено
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        // Этот метод сработает, если сообщение будет удалено
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Этот метод сработает, если сообщение будет перемещено
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Обработка ошибок
                    }
                });
    }

    @Override
    public Observable<List<ChatInfoForLoad>> loadChats(UserData userData) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_CHATS_DATA);
        return Observable.create(emitter -> {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    List<ChatInfoForLoad> chats = new ArrayList<>();
                    List<String> usersToFetch = new ArrayList<>();

                    for (DataSnapshot data : snapshot.getChildren()) {
                        String chatId = data.getKey();

                        if (userData.getChatIds().contains(chatId)) {
                            ChatInfoForLoad chat = data.getValue(ChatInfoForLoad.class);
                            if (chat != null && chat.getNumberOfMessages()!=null && chat.getNumberOfMessages()!=0) {
                                chat.setChatId(chatId);
                                String anotherUserId = (chat.getFirstUser().equals(userData.getUserId())) ? chat.getSecondUser() : chat.getFirstUser();
                                usersToFetch.add(anotherUserId);
                                chats.add(chat);
                            }
                        }
                    }

                    if (!usersToFetch.isEmpty()) {

                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA);
                        for (String userId : usersToFetch) {

                            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {

                                        String nameAndSurname = snapshot.child(USER_NAME).getValue(String.class) + " " + snapshot.child(USER_SURNAME).getValue(String.class);
                                        String imageUri= snapshot.child(USER_AVATAR_IMAGE).getValue(String.class);
                                        if (imageUri==null) imageUri="";

                                        boolean updated = false;
                                        for (ChatInfoForLoad chat : chats) {
                                            String anotherUserId = (chat.getFirstUser().equals(userData.getUserId())) ? chat.getSecondUser() : chat.getFirstUser();
                                            if (anotherUserId.equals(userId)) {
                                                chat.setAnotherUserNameAndSurname(nameAndSurname);
                                                chat.setAnotherUserAvatar(imageUri);
                                                updated = true;
                                                break;
                                            }
                                        }

                                        if (!updated) {
                                        }
                                    } else {
                                    }

                                    if (chats.size() == usersToFetch.size()) {
                                        emitter.onNext(chats);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("LoadChats", "Error loading user data for user: " + userId, error.toException());
                                    emitter.onError(error.toException());
                                }
                            });
                        }
                    } else {
                        Log.d("LoadChats", "No additional user data needed, sending chats");
                        emitter.onNext(chats);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("LoadChats", "Error loading chats data", error.toException());
                    emitter.onError(error.toException());
                }
            });
        });
    }

    @Override
    public void addImage(ImageToDb imageToDb) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageToDb.getImageId());
        storageRef.putBytes(imageToDb.getImageData())
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    saveImageUrl(uri.toString(),imageToDb.getUserId());
                }))
                .addOnFailureListener(e -> Log.e("Firebase", "Ошибка загрузки", e));
    }

    @Override
    public void userAvatarListenerById(String userId, OnImageUrlFetchedListener onImageUrlFetchedListener) {
        DatabaseReference databaseRef=FirebaseDatabase.getInstance().getReference().child(DATABASE_WITH_USERS_DATA).child(userId).child(USER_AVATAR_IMAGE);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String imageUri=snapshot.getValue(String.class);
                    onImageUrlFetchedListener.onSuccess(imageUri);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onImageUrlFetchedListener.onFailure(new Exception("Изображение не найдено"));
            }
        });
    }

    @Override
    public void removeFriend(UserData currentUser, UserData anotherUser) {
        FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA).child(currentUser.getUserId()).child(USER_FRIENDS_IDS).setValue(currentUser.getFriendsIds());
        FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).child(USER_FRIENDS_IDS).setValue(anotherUser.getFriendsIds());

        FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).child(USER_TASK_TO_FRIEND).setValue(anotherUser.getTaskToFriendsIds());
        FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA).child(currentUser.getUserId()).child(USER_SUBSCRIBERS_IDS).setValue(currentUser.getSubscribersIds());
    }

    @Override
    public void unsubscribeFromUser(UserData currentUser, UserData anotherUser) {
        FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA).child(anotherUser.getUserId()).child(USER_SUBSCRIBERS_IDS).setValue(anotherUser.getSubscribersIds());
        FirebaseDatabase.getInstance().getReference(DATABASE_WITH_USERS_DATA).child(currentUser.getUserId()).child(USER_TASK_TO_FRIEND).setValue(currentUser.getTaskToFriendsIds());
    }

    private void saveImageUrl(String imageId,String userId) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child(DATABASE_WITH_USERS_DATA).child(userId).child(USER_AVATAR_IMAGE);
        if (imageId != null) {
            databaseRef.setValue(imageId);
        }
    }
}



