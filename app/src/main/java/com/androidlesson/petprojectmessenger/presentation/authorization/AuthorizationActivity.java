package com.androidlesson.petprojectmessenger.presentation.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.models.LoginData;
import com.androidlesson.domain.authorization.models.RegistrationData;
import com.androidlesson.domain.authorization.repository.CallbackAuthorizaiton;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.MainActivity;

import javax.inject.Inject;


public class AuthorizationActivity extends AppCompatActivity {

    private EditText et_email,et_password,et_repassword;
    private TextView tv_go_to_log,tv_go_to_reg;
    private Button b_reg,b_log;
    private LinearLayout ll_login,ll_registration,ll_main_auth_layout;
    private RelativeLayout rl_progress_bar;

    private AuthorizationViewModel vm;

    @Inject
    AuthorizationViewModelFactory authorizationViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        initialization();

        checkCurrUser();

        observeLiveData(this);

        setOnClickListener();
    }

    private void initialization(){
        ((App) getApplication()).appComponent.inject(this);
        vm=new ViewModelProvider(this,authorizationViewModelFactory).get(AuthorizationViewModel.class);

        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        et_repassword=findViewById(R.id.et_repassword);
        tv_go_to_log=findViewById(R.id.tv_go_to_log);
        tv_go_to_reg=findViewById(R.id.tv_go_to_reg);
        b_log=findViewById(R.id.b_log);
        b_reg=findViewById(R.id.b_reg);
        ll_registration=findViewById(R.id.ll_registration);
        ll_login=findViewById(R.id.ll_login);
        ll_main_auth_layout=findViewById(R.id.ll_main_authorization_layout);
        rl_progress_bar=findViewById(R.id.rl_authorization_progress_bar_layout);
    }

    private void checkCurrUser(){
        vm.checkCurrentUser();
    }

    private void observeLiveData(Context context){
        vm.goToMainActivityLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) context.startActivity(new Intent(context, MainActivity.class));
            }
        });

        vm.loginVisibilityLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                ll_login.setVisibility(visibility);
            }
        });

        vm.registrationVisibilitySceneLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                ll_registration.setVisibility(visibility);
            }
        });

        vm.errorAuthorizationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                showToast(string);
            }
        });

        vm.progressBarSceneLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    rl_progress_bar.setVisibility(View.VISIBLE);
                    ll_main_auth_layout.setClickable(false);
                }
                else {
                    rl_progress_bar.setVisibility(View.INVISIBLE);
                    ll_main_auth_layout.setClickable(true);
                }
            }
        });
    }

    private void setOnClickListener(){

        tv_go_to_reg.setOnClickListener(v->{
            vm.registerScene();
        });

        tv_go_to_log.setOnClickListener(v->{
            vm.loginScene();
        });

        b_reg.setOnClickListener(v->{
            String email=et_email.getText().toString().trim();
            String password=et_password.getText().toString().trim();
            String repassword=et_repassword.getText().toString().trim();

            vm.registration(new RegistrationData(email,password,repassword));
        });

        b_log.setOnClickListener(v->{
            String email=et_email.getText().toString().trim();
            String password=et_password.getText().toString().trim();

            vm.login(new LoginData(email,password));
        });
    }

    private void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}