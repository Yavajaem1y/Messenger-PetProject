package com.androidlesson.petprojectmessenger.presentation.main.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToEdit;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.editUserDataActivityViewModel.EditUserDataActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.editUserDataActivityViewModel.EditUserDataActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class EditUserDataActivity extends AppCompatActivity {

    private EditText et_name,et_surname, et_more_info;
    private TextView tv_save;

    private SharedViewModel sharedViewModel;
    private EditUserDataActivityViewModel vm;

    @Inject
    SharedViewModelFactory sharedViewModelFactory;
    @Inject
    EditUserDataActivityViewModelFactory editUserDataActivityViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);

        initialization();

        setOnClickListener();

        setObserver();

    }

    private void initialization(){
        ((App) getApplication()).appComponent.injectEditUserDataActivity(this);

        sharedViewModel= new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);
        vm=new ViewModelProvider(this,editUserDataActivityViewModelFactory).get(EditUserDataActivityViewModel.class);

        et_name=findViewById(R.id.et_current_user_name);
        et_surname=findViewById(R.id.et_current_user_surname);
        et_more_info=findViewById(R.id.et_more_info);
        tv_save=findViewById(R.id.b_save_curr_user_data);
    }

    private void setOnClickListener(){
        tv_save.setOnClickListener(v->{
            String name=et_name.getText().toString().trim();
            String surname=et_surname.getText().toString().trim();
            String moreInfo=et_more_info.getText().toString().trim();

            if (!name.isEmpty() && !surname.isEmpty()){
                vm.editData(new UserDataToEdit(name,surname,moreInfo));
            }
            else showToast("The required fields must be filled in");
        });
    }

    private void setObserver(){
        sharedViewModel.getUserData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    vm.saveUserData(userData);
                    if (et_name.getText().toString().trim().isEmpty()){
                        et_name.setText(userData.getUserName());
                    }
                    if (et_surname.getText().toString().trim().isEmpty()){
                        et_surname.setText(userData.getUserSurname());
                    }
                }
            }
        });

        vm.getFinishLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean!=null && aBoolean){
                    finish();
                }
            }
        });

    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}