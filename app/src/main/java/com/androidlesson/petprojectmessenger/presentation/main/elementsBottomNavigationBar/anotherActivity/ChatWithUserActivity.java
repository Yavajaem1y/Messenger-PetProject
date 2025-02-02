package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.anotherActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel.ChatWithUserActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class ChatWithUserActivity extends AppCompatActivity {

    private EditText et_with_message;
    private ImageView iv_send_a_message;
    private TextView tv_another_user_name_and_surname;

    private ChatWithUserActivityViewModel vm;
    private SharedViewModel sharedViewModel;

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
    }

    private void initialization(){
        ((App) getApplication()).appComponent.injectChatWithUserActivity(this);
        vm=new ViewModelProvider(this,chatWithUserActivityViewModelFactory).get(ChatWithUserActivityViewModel.class);
        sharedViewModel=new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);

        iv_send_a_message=findViewById(R.id.iv_send_a_message);
        et_with_message=findViewById(R.id.et_write_your_message);
        tv_another_user_name_and_surname=findViewById(R.id.tv_user_name_and_surname);

        String chatId=getIntent().getStringExtra("CHAT_ID");
        if (chatId!=null && vm.getChatId()==null) vm.setChatId(chatId);
    }

    private void observable(){
        sharedViewModel.getUserData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    vm.setCurrentUser(userData);
                }
            }
        });

        vm.getAnotherUserLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    tv_another_user_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
                }
            }
        });
    }

    private void setOnClickListener(){
        iv_send_a_message.setOnClickListener(v->{
            vm.sendAMessage(et_with_message.getText().toString());
            et_with_message.setText("");
        });
    }

}