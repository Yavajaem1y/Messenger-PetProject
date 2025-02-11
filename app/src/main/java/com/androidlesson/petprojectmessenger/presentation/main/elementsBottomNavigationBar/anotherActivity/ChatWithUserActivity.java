package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.anotherActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.adapter.MessageAdapter;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class ChatWithUserActivity extends AppCompatActivity {

    private EditText et_with_message;
    private ImageView iv_send_a_message;
    private TextView tv_another_user_name_and_surname;
    private RecyclerView rv_all_chats;

    private ChatWithUserActivityViewModel vm;
    private SharedViewModel sharedViewModel;

    private MessageAdapter adapter;

    @Inject
    ChatWithUserActivityViewModelFactory chatWithUserActivityViewModelFactory;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_user);

        initialization();

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

        adapter = new MessageAdapter();
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
            Log.d("DDD","new messages loaded " + String.valueOf(messages.size()));

            if (previousCount == 0) {
                rv_all_chats.post(() -> rv_all_chats.scrollToPosition(adapter.getItemCount() - 1));
            } else {
                ((LinearLayoutManager) rv_all_chats.getLayoutManager()).scrollToPositionWithOffset(
                        firstVisiblePosition + messages.size(), topOffset
                );
            }

            isLoading = false;
        });

        vm.getNewMessagesUserLiveData().observe(this, new Observer<ChatInfo.Message>() {
            @Override
            public void onChanged(ChatInfo.Message message) {
                if (message!=null){
                    adapter.addMessageToEnd(message);
                    rv_all_chats.post(() -> rv_all_chats.scrollToPosition(adapter.getItemCount() - 1));
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

}