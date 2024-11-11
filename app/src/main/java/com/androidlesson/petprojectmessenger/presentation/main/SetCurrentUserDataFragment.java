package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.petprojectmessenger.databinding.FragmentSetCurrentUserDataBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel.MainActivityViewModel;

public class SetCurrentUserDataFragment extends Fragment {

    private FragmentSetCurrentUserDataBinding binding;

    private EditText et_name,et_surname;
    private Button b_save_data;

    private MainActivityViewModel vm;

    public SetCurrentUserDataFragment(MainActivityViewModel vm) {
        this.vm = vm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentSetCurrentUserDataBinding.inflate(inflater, container, false);

        initialization();

        setOnClickListener();

        return binding.getRoot();
    }

    private void initialization(){

        et_name=binding.etCurrentUserName;
        et_surname=binding.etCurrentUserSurname;
        b_save_data=binding.bSaveCurrUserData;
    }

    private void setOnClickListener(){
        b_save_data.setOnClickListener(v->{
            String name=et_name.getText().toString().trim();
            String surname=et_surname.getText().toString().trim();
            if (name.isEmpty() || surname.isEmpty()) {
                showToast("The fields should not be empty");
            }
            else{
                vm.saveCurrUserData(new FullUserData("",name,surname));
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }




}