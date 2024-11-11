package com.androidlesson.data.main.repositoryForUseCase;

import androidx.annotation.NonNull;

import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.callbacks.CallbackCurrentUserInfoFromFireBase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class MainFirebaseRepositoryImpl implements MainFirebaseRepository {
    private final String USERS_DATABASE="USER_DATABASE";
    private final String USER_NAME="userName";
    private final String USER_SURNAME ="userSurname";

    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    FirebaseStorage storage=FirebaseStorage.getInstance();

    @Override
    public boolean logout() {
        firebaseAuth.signOut();
        return true;
    }

    @Override
    public boolean setCurrentUserData(FullUserData fullUserData) {
        database.getReference(USERS_DATABASE).child(firebaseAuth.getUid().toString()).setValue(fullUserData);
        return false;
    }

    @Override
    public void getCurrentUserData(CallbackCurrentUserInfoFromFireBase callbackCurrentUserInfoFromFireBase) {
        try {
            String userId = firebaseAuth.getUid();
            if (userId == null) {
                callbackCurrentUserInfoFromFireBase.callbackUserInfo(null);
                return;
            }

            // Доступ к нужной записи в базе данных
            database.getReference(USERS_DATABASE).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child(USER_NAME).getValue(String.class);
                        String surname = snapshot.child(USER_SURNAME).getValue(String.class);

                        if (name != null && surname != null) {
                            callbackCurrentUserInfoFromFireBase.callbackUserInfo(new FullUserData(name, surname));
                        } else {
                            callbackCurrentUserInfoFromFireBase.callbackUserInfo(null);
                        }
                    } else {
                        callbackCurrentUserInfoFromFireBase.callbackUserInfo(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callbackCurrentUserInfoFromFireBase.callbackUserInfo(null);
                }
            });
        } catch (Exception e) {

            callbackCurrentUserInfoFromFireBase.callbackUserInfo(null);
        }
    }

    @Override
    public String getCurrentUserId() {
        return firebaseAuth.getUid().toString();
    }

    @Override
    public void setCurrnetUserAvatar(byte[] image) {
        storage.getReference("usersAvatar/").child(getCurrentUserId()).putBytes(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }
}
