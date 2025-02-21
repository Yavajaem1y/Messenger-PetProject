package com.androidlesson.petprojectmessenger.presentation.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel.AnotherUserProfileActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel.AnotherUserProfileActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnotherUserProfileActivity extends AppCompatActivity {

    private AnotherUserProfileActivityViewModel vm;
    @Inject
    AnotherUserProfileActivityViewModelFactory vmFactory;

    private SharedViewModel sharedViewModel;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private CircleImageView civ_userAvatar;
    private TextView tv_userNameAndSurname, tv_userId,tv_number_of_friends;
    private ScrollView scrollView_main;
    private RelativeLayout relativeLayout_bottom, rl_main, rl_progress_bar;
    private ImageView iv_dots_menu;
    private LinearLayout b_add_to_friends, b_send_a_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user_profile);

        initialization();

        setObserver();

        setOnClickListener();

        addOnScrollListener();
    }

    private void initialization() {
        ((App) getApplication()).appComponent.injectAnotherProfileActivity(this);
        vm = new ViewModelProvider(this, vmFactory).get(AnotherUserProfileActivityViewModel.class);
        sharedViewModel = new ViewModelProvider(this, sharedViewModelFactory).get(SharedViewModel.class);

        civ_userAvatar = findViewById(R.id.civ_another_user_avatar);
        tv_userNameAndSurname = findViewById(R.id.tv_another_user_name_and_surname);
        tv_userId = findViewById(R.id.tv_another_user_id);
        scrollView_main = findViewById(R.id.sv_main_layout);
        relativeLayout_bottom = findViewById(R.id.rl_bottom_layout);
        iv_dots_menu = findViewById(R.id.iv_dots_menu);
        b_add_to_friends = findViewById(R.id.ll_button_add_to_friends);
        b_send_a_message = findViewById(R.id.ll_button_send_a_message);
        rl_progress_bar = findViewById(R.id.rl_progress_bar);
        rl_main = findViewById(R.id.rl_main);
        tv_number_of_friends=findViewById(R.id.tv_numbers_of_friends);

        boolean isCurrUserSet = false;

        UserData anotherUserData = (UserData) getIntent().getSerializableExtra("ANOTHER_USER_DATA");
        UserData currUserData = (UserData) getIntent().getSerializableExtra("CURRENT_USER_DATA");

        if (currUserData != null) {
            Log.d("AnotherUserProfile", "Current user set from Intent: " + currUserData.getUserId());
            vm.setCurrentUser(currUserData);
        }

        sharedViewModel.getUserData().observe(this, userData -> {
            if (userData != null) {
                Log.d("AnotherUserProfile", "Current user set from SharedViewModel: " + userData.getUserId());
                vm.setCurrentUser(userData);
            } else {
                Log.d("AnotherUserProfile", "SharedViewModel data is still null");
            }
        });

        if (anotherUserData != null) {
            Log.d("AnotherUserProfile", "Loading another user data: " + anotherUserData.getUserId());
            vm.loadUserData(anotherUserData);
        } else {
            Log.d("AnotherUserProfile", "Another user data from Intent is null");
        }
    }

    private void setObserver() {
        vm.getAnotherUserDataLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData user) {
                if (user != null) {
                    tv_userNameAndSurname.setText(user.getUserName() + " " + user.getUserSurname());
                    tv_userId.setText("@" + user.getUserId());
                    tv_number_of_friends.setText(user.getFriendsIds().size() + " " +((user.getFriendsIds().size()>1) ? "friends" : "friend"));
                    Log.d("AnotherUserProfile", "User data updated: " + user.getUserId());
                } else {
                    Log.d("AnotherUserProfile", "Another user data is null");
                }
            }
        });

        vm.getVisibilityTopElementLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    relativeLayout_bottom.animate().alpha(1f).setDuration(300).start();
                    relativeLayout_bottom.setVisibility(View.VISIBLE);
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.white));
                } else {
                    relativeLayout_bottom.animate().alpha(0f).setDuration(300).start();
                    relativeLayout_bottom.setVisibility(View.GONE);
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.accent_color));
                }
            }
        });

        sharedViewModel.getUserData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null) {
                    Log.d("AnotherUserProfile", "Current user from SharedViewModel: " + userData.getUserId());
                    vm.setCurrentUser(userData);
                } else {
                    Log.d("AnotherUserProfile", "SharedViewModel data is null");
                }
            }
        });

        vm.getVisibilityLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    rl_progress_bar.setVisibility(View.INVISIBLE);
                    rl_main.setVisibility(View.VISIBLE);
                }
            }
        });

        vm.getChatIdLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String chatID) {
                if (chatID!=null) {
                    Intent intent=new Intent(getApplication(), ChatWithUserActivity.class);
                    intent.putExtra("CHAT_ID",chatID);
                    startActivity(intent);
                }
            }
        });
    }

    private void setOnClickListener() {
        b_add_to_friends.setOnClickListener(v -> {
            vm.addToFriend();
        });

        b_send_a_message.setOnClickListener(v -> {
            vm.sendAMessage();
        });
    }

    private void addOnScrollListener() {
        scrollView_main.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView_main.getScrollY();

            if (scrollY > 0) {
                vm.setVisibilityTopElement(1);
            } else {
                vm.setVisibilityTopElement(0);
            }
        });
    }
}