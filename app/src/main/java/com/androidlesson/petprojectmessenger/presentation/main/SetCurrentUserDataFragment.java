package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.Error;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.petprojectmessenger.databinding.FragmentSetCurrentUserDataBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.setCurrentUserDataFragmentViewModel.SetCurrentUserDataFragmentVM;
import com.androidlesson.petprojectmessenger.presentation.main.di.setCurrentUserDataFragmentViewModel.SetCurrentUserDataFragmentVMFactory;

public class SetCurrentUserDataFragment extends Fragment {

    private FragmentSetCurrentUserDataBinding binding;

    private EditText et_name,et_surname;
    private Button b_save_data;

    private SetCurrentUserDataFragmentVM vm;

    private CallbackGetUserData callbackGetUserData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentSetCurrentUserDataBinding.inflate(inflater, container, false);

        initialization();

        setObserver();

        setOnClickListener();

        return binding.getRoot();
    }

    private void initialization(){
        vm= new ViewModelProvider(this,new SetCurrentUserDataFragmentVMFactory(getContext())).get(SetCurrentUserDataFragmentVM.class);

        et_name=binding.etCurrentUserName;
        et_surname=binding.etCurrentUserSurname;
        b_save_data=binding.bSaveCurrUserData;
    }

    private void setObserver(){
        vm.getUserDataLiveData().observe(getActivity(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
            }
        });

        vm.getErrorLiveData().observe(getActivity(), new Observer<Error>() {
            @Override
            public void onChanged(Error error) {
                showToast(error.getErrorMessage());
            }
        });
    }

    private void setOnClickListener(){
        b_save_data.setOnClickListener(v->{
            String name=et_name.getText().toString().trim();
            String surname=et_surname.getText().toString().trim();
            vm.SaveUserData(new UserNameAndSurname(name,surname));
        });
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }





}