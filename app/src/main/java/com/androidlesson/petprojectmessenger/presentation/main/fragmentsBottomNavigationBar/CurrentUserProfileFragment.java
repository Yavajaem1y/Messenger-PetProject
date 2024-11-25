package com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.databinding.FragmentCurrentUserProfileBinding;
import com.androidlesson.petprojectmessenger.presentation.main.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableCallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableUserData;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModelFactory;


import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUserProfileFragment extends Fragment {

    private FragmentCurrentUserProfileBinding binding;
    private CurrentUserProfileViewModel vm;

    private ImageView iv_logout;
    private TextView tv_name_and_surname;
    private CircleImageView ciw_profile_avatar,ciw_add_profile_photo;

    //User data from fragment
    private UserData currUserData;
    private CallbackLogOut callbackLogOut;


    public static MainFragment newInstance(SerializableUserData serializableUserData, SerializableCallbackLogOut serializableCallbackLogOut) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("USERDATA",serializableUserData);
        args.putSerializable("CALLBACK_LOG_OUT",serializableCallbackLogOut);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            currUserData=((SerializableUserData)getArguments().get("USERDATA")).getUserData();
            callbackLogOut=((SerializableCallbackLogOut)getArguments().get("CALLBACK_LOG_OUT")).getCallbackLogOut();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentCurrentUserProfileBinding.inflate(inflater, container, false);

        init();

        setObserver();

        setOnClickListener();

        return binding.getRoot();
    }

    private void init() {
        vm=new ViewModelProvider(requireActivity(),new CurrentUserProfileViewModelFactory(getContext())).get(CurrentUserProfileViewModel.class);

        vm.setVMInfo(currUserData);

        iv_logout=binding.ivLogout;
        tv_name_and_surname=binding.tvCurrUserNameAndSurname;
        ciw_profile_avatar=binding.civCurrUserAvatar;
        ciw_add_profile_photo=binding.ciwAddProfilePhoto;
    }

    private void setObserver(){
        vm.getUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                tv_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
            }
        });
    }

    private void setOnClickListener(){
        iv_logout.setOnClickListener(v->{
            callbackLogOut.logout();
        });
    }
}