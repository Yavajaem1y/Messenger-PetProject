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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments.DotsMenuFragmentFromAnotherUserActivity;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments.MoreInfoDialogFragment;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel.AnotherUserProfileActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel.AnotherUserProfileActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;

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
    private TextView tv_userNameAndSurname, tv_userId,tv_number_of_friends,tv_more_info;
    private ScrollView scrollView_main;
    private RelativeLayout relativeLayout_bottom, rl_main, rl_progress_bar;
    private ImageView iv_dots_menu;
    private LinearLayout b_add_to_friends, b_send_a_message, b_subscribe, b_unsubscribe, b_delete_friend;

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
        b_delete_friend=findViewById(R.id.ll_button_delete_friend);
        b_subscribe=findViewById(R.id.ll_button_subscribe);
        b_unsubscribe=findViewById(R.id.ll_button_unsubscribe);
        tv_more_info=findViewById(R.id.tv_more_info);

        boolean isCurrUserSet = false;

        UserData anotherUserData = (UserData) getIntent().getSerializableExtra("ANOTHER_USER_DATA");
        UserData currUserData = (UserData) getIntent().getSerializableExtra("CURRENT_USER_DATA");

        if (currUserData != null) {
            vm.setCurrentUser(currUserData);
        }

        sharedViewModel.getUserData().observe(this, userData -> {
            if (userData != null) {
                vm.setCurrentUser(userData);
            }
        });

        if (anotherUserData != null) {
            vm.loadUserData(anotherUserData);
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
                    if (user.getImageData()!=null && !user.getImageData().isEmpty()){
                        Glide.with(getApplicationContext()).load(user.getImageData()).into(civ_userAvatar);
                    }

                } else {
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
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.secondary_accent_color));
                }
            }
        });

        sharedViewModel.getUserData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null) {
                    vm.setCurrentUser(userData);
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

        vm.getButtonSceneLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==0){
                    b_subscribe.setVisibility(View.VISIBLE);
                    b_unsubscribe.setVisibility(View.INVISIBLE);
                    b_delete_friend.setVisibility(View.INVISIBLE);
                    b_add_to_friends.setVisibility(View.INVISIBLE);
                }
                else if (integer==1){
                    b_subscribe.setVisibility(View.INVISIBLE);
                    b_unsubscribe.setVisibility(View.VISIBLE);
                    b_delete_friend.setVisibility(View.INVISIBLE);
                    b_add_to_friends.setVisibility(View.INVISIBLE);
                }
                else if (integer==2){
                    b_subscribe.setVisibility(View.INVISIBLE);
                    b_unsubscribe.setVisibility(View.INVISIBLE);
                    b_delete_friend.setVisibility(View.INVISIBLE);
                    b_add_to_friends.setVisibility(View.VISIBLE);
                }
                else if (integer==3){
                    b_subscribe.setVisibility(View.INVISIBLE);
                    b_unsubscribe.setVisibility(View.INVISIBLE);
                    b_delete_friend.setVisibility(View.VISIBLE);
                    b_add_to_friends.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setOnClickListener() {
        b_add_to_friends.setOnClickListener(v -> {
            vm.addToFriend();
        });

        b_subscribe.setOnClickListener(v->{
            vm.addToFriend();
        });

        b_send_a_message.setOnClickListener(v -> {
            vm.sendAMessage();
        });

        b_delete_friend.setOnClickListener(v->{
            vm.deleteAFriend();
        });

        b_unsubscribe.setOnClickListener(v->{
            vm.deleteAFriend();
        });

        iv_dots_menu.setOnClickListener(v->{
            FragmentManager fragmentManager = getSupportFragmentManager();
            DotsMenuFragmentFromAnotherUserActivity dialogFragment = new DotsMenuFragmentFromAnotherUserActivity();
            dialogFragment.show(fragmentManager, "my_dialog");
        });

        tv_more_info.setOnClickListener(v->{
            FragmentManager fragmentManager = getSupportFragmentManager();
            MoreInfoDialogFragment dialogFragment = new MoreInfoDialogFragment(vm.getAnotherUserDataLiveData().getValue().getUserInfo());
            dialogFragment.show(fragmentManager, "my_dialog");
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