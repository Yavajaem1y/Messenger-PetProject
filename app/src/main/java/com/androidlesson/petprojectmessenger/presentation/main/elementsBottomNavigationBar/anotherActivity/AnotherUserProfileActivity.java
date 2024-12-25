package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.anotherActivity;

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

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnotherUserProfileActivity extends AppCompatActivity {

    private AnotherUserProfileActivityViewModel vm;
    @Inject
    AnotherUserProfileActivityViewModelFactory vmFactory;

    private UserData currUser;

    private CircleImageView civ_userAvatar;
    private TextView tv_userNameAndSurname, tv_userId;
    private ScrollView scrollView_main;
    private RelativeLayout relativeLayout_bottom;
    private ImageView iv_dots_menu;
    private LinearLayout b_add_to_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user_profile);

        initalization();

        setObserver();

        setOnClickListener();

        addOnScrollListener();
    }

    private void initalization(){
        ((App)getApplication()).appComponent.injectAnotherProfileActivity(this);
        vm= new ViewModelProvider(this,vmFactory).get(AnotherUserProfileActivityViewModel.class);

        civ_userAvatar =findViewById(R.id.civ_another_user_avatar);
        tv_userNameAndSurname =findViewById(R.id.tv_another_user_name_and_surname);
        tv_userId =findViewById(R.id.tv_another_user_id);
        scrollView_main=findViewById(R.id.sv_main_layout);
        relativeLayout_bottom=findViewById(R.id.rl_bottom_layout);
        iv_dots_menu=findViewById(R.id.iv_dots_menu);
        b_add_to_friends=findViewById(R.id.ll_button_add_to_friends);

        UserData userData = (UserData) getIntent().getSerializableExtra("ANOTHER_USER_DATA");
        currUser=(UserData) getIntent().getSerializableExtra("CURRENT_USER_DATA");
        if (userData != null) {
            vm.setUserData(userData);
        }
    }

    private void setObserver(){
        vm.getAnotherUserDataLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData user) {
                tv_userNameAndSurname.setText(user.getUserName()+" "+user.getUserSurname());
                tv_userId.setText("@"+user.getUserId());
            }
        });

        vm.getVisibilityTopElementLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    relativeLayout_bottom.animate().alpha(1f).setDuration(300).start();
                    relativeLayout_bottom.setVisibility(View.VISIBLE);
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.white));
                }
                else{
                    relativeLayout_bottom.animate().alpha(0f).setDuration(300).start();
                    relativeLayout_bottom.setVisibility(View.GONE);
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.accent_color));
                }
            }
        });
    }

    private void setOnClickListener(){
        b_add_to_friends.setOnClickListener(v->{
            vm.addToFriend(currUser);
        });
    }

    private void addOnScrollListener(){
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