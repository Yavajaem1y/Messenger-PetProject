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

import com.androidlesson.domain.main.models.Error;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentSetCurrentUserDataBinding;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackWithUserData;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.setCurrentUserDataFragmentViewModel.SetCurrentUserDataFragmentVM;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.setCurrentUserDataFragmentViewModel.SetCurrentUserDataFragmentVMFactory;

import javax.inject.Inject;

public class SetCurrentUserDataFragment extends Fragment {

    private FragmentSetCurrentUserDataBinding binding;

    private EditText et_name,et_surname,et_user_id;
    private Button b_save_data;

    private SetCurrentUserDataFragmentVM vm;
    @Inject
    SetCurrentUserDataFragmentVMFactory setCurrentUserDataFragmentVMFactory;

    private CallbackWithUserData callbackUserDataIsSaved;

    public SetCurrentUserDataFragment(CallbackWithUserData callbackUserDataIsSaved) {
        this.callbackUserDataIsSaved = callbackUserDataIsSaved;
    }

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
        ((App) requireActivity().getApplication()).appComponent.injectSetCurrentUserDataFragment(this);
        vm= new ViewModelProvider(this,setCurrentUserDataFragmentVMFactory).get(SetCurrentUserDataFragmentVM.class);

        et_name=binding.etCurrentUserName;
        et_surname=binding.etCurrentUserSurname;
        b_save_data=binding.bSaveCurrUserData;
        et_user_id=binding.etCurrentUserId;
    }

    private void setObserver(){
        vm.getUserDataLiveData().observe(getActivity(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                callbackUserDataIsSaved.getUserData(userData);
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
            String userId=et_user_id.getText().toString().trim();
            vm.SaveUserData(new UserData(userId,null,name,surname));
        });
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }





}