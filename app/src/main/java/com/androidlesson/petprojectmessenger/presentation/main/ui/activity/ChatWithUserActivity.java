package com.androidlesson.petprojectmessenger.presentation.main.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.ui.adapters.MessageAdapter;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWithUserActivity extends AppCompatActivity {

    private EditText et_with_message;
    private ImageView iv_send_a_message,iv_dots_menu,iv_add_image,iv_remove_image,iv_image;
    private TextView tv_another_user_name_and_surname;
    private RecyclerView rv_all_chats;
    private CircleImageView civ_user_avatar;
    private RelativeLayout rl_go_to_user_profile,rl_add_image;

    private ChatWithUserActivityViewModel vm;
    private SharedViewModel sharedViewModel;

    private MessageAdapter adapter;

    @Inject
    ChatWithUserActivityViewModelFactory chatWithUserActivityViewModelFactory;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_user);

        initialization();

        setImagePickerLauncher();

        observable();

        setOnClickListener();

        setOnScrollListener();
    }

    private void initialization() {

        ((App) getApplication()).appComponent.injectChatWithUserActivity(this);
        vm = new ViewModelProvider(this, chatWithUserActivityViewModelFactory).get(ChatWithUserActivityViewModel.class);
        sharedViewModel = new ViewModelProvider(this, sharedViewModelFactory).get(SharedViewModel.class);

        iv_send_a_message = findViewById(R.id.iv_send_a_message);
        et_with_message = findViewById(R.id.et_write_your_message);
        tv_another_user_name_and_surname = findViewById(R.id.tv_user_name_and_surname);
        rv_all_chats = findViewById(R.id.rv_all_chats);
        civ_user_avatar= findViewById(R.id.civ_user_avatar);
        rl_go_to_user_profile=findViewById(R.id.rl_go_to_user_profile);
        iv_dots_menu=findViewById(R.id.iv_dots_menu);
        iv_add_image=findViewById(R.id.iv_add_image);
        iv_remove_image=findViewById(R.id.iv_remove_image);
        iv_image=findViewById(R.id.iv_image);
        rl_add_image=findViewById(R.id.rl_add_image);

        iv_dots_menu.setColorFilter(getResources().getColor(R.color.secondary_accent_color));

        adapter = new MessageAdapter(getSupportFragmentManager(),getApplicationContext());
        rv_all_chats.setLayoutManager(new LinearLayoutManager(this));
        rv_all_chats.setAdapter(adapter);
        List<ChatInfo.Message> messages =vm.getMessagesUserLiveData().getValue();
        if (messages!=null && messages.size()!=0){
            adapter.addMessagesToStart(messages);
        }

        String chatId = getIntent().getStringExtra("CHAT_ID");
        if (chatId != null && vm.getChatId() == null) {
            vm.setChatId(chatId);
            vm.loadMessages(null);
        }
    }

    private void setImagePickerLauncher(){
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();

                        try {
                            Context context = getApplicationContext();
                            if (context == null) return;

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                            byte[] imageData = convertBitmapToByteArray(bitmap);

                            vm.addImage(imageData);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }


    private void observable() {

        sharedViewModel.getUserData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null) {
                    vm.setCurrentUser(userData);
                    adapter.setCurrentUser(userData);
                }
            }
        });

        vm.getAnotherUserLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null) {
                    tv_another_user_name_and_surname.setText(userData.getUserName() + " " + userData.getUserSurname());
                    Log.d("image",userData.getImageData());
                    if (!TextUtils.isEmpty(userData.getImageData())) {
                        Glide.with(ChatWithUserActivity.this).load(userData.getImageData()).into(civ_user_avatar);
                    }
                }
            }
        });

        vm.getMessagesUserLiveData().observe(this, messages -> {
            if (messages == null || messages.isEmpty()) return;

            int previousCount = adapter.getItemCount();
            int firstVisiblePosition = ((LinearLayoutManager) rv_all_chats.getLayoutManager()).findFirstVisibleItemPosition();
            View firstVisibleView = rv_all_chats.getLayoutManager().findViewByPosition(firstVisiblePosition);
            int topOffset = (firstVisibleView != null) ? firstVisibleView.getTop() : 0;

            adapter.addMessagesToStart(messages);

            if (previousCount == 0) {
                rv_all_chats.post(() -> rv_all_chats.scrollToPosition(adapter.getItemCount() - 1));
            } else {
                ((LinearLayoutManager) rv_all_chats.getLayoutManager()).scrollToPositionWithOffset(
                        firstVisiblePosition + messages.size(), topOffset
                );
            }
            isLoading = false;
        });

        vm.getImageInMessageLiveData().observe(this, new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] bytes) {
                if (bytes!=null){
                    rl_add_image.setVisibility(View.VISIBLE);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    iv_image.setImageBitmap(bitmap);
                }
                else {
                    rl_add_image.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setOnClickListener() {
        iv_send_a_message.setOnClickListener(v -> {
            String messageText = et_with_message.getText().toString();
            vm.sendAMessage(messageText);
            et_with_message.setText("");
        });

        rl_go_to_user_profile.setOnClickListener(v->{
            if (vm.getAnotherUserLiveData().getValue()!=null){
                Intent intent=new Intent(this, AnotherUserProfileActivity.class);
                intent.putExtra("ANOTHER_USER_DATA",vm.getAnotherUserLiveData().getValue());
                startActivity(intent);
            }
        });

        iv_add_image.setOnClickListener(v->{
            pickImageFromGallery();
        });

        iv_remove_image.setOnClickListener(v->{
            vm.removeImage();
        });
    }

    private boolean isLoading = false;

    private void setOnScrollListener() {
        rv_all_chats.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();

                    if (firstVisibleItemPosition <= 5 && !isLoading) {
                        loadMoreMessages();
                    }
                }
            }
        });
    }

    private void loadMoreMessages() {
        if (vm.getChatId() != null) {
            if (vm.getMessagesUserLiveData().getValue()!=null){
                String firstMessageTimestamp = vm.getMessagesUserLiveData().getValue().get(0).getTimeSending();
                vm.loadMessages(firstMessageTimestamp);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        LinearLayoutManager layoutManager = (LinearLayoutManager) rv_all_chats.getLayoutManager();
        if (layoutManager != null) {
            int scrollPosition = layoutManager.findFirstVisibleItemPosition();
            outState.putInt("scroll_position", scrollPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            int scrollPosition = savedInstanceState.getInt("scroll_position", 0);
            LinearLayoutManager layoutManager = (LinearLayoutManager) rv_all_chats.getLayoutManager();
            if (layoutManager != null) {
                layoutManager.scrollToPositionWithOffset(scrollPosition, 0);
            }
        }
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

}