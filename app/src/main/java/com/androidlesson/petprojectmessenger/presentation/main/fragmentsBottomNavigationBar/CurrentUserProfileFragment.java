package com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar;

import android.os.Bundle;

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
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModelFactory;


import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUserProfileFragment extends Fragment {

    private FragmentCurrentUserProfileBinding binding;
    private CurrentUserProfileViewModel vm;

    private ImageView iv_logout;
    private TextView tv_name_and_surname;
    private CircleImageView ciw_profile_avatar,ciw_add_profile_photo;

    private UserData userData;
    private CallbackLogOut callbackLogOut;
    public CurrentUserProfileFragment(UserData userData, CallbackLogOut callbackLogOut) {
        this.userData = userData;
        this.callbackLogOut=callbackLogOut;
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
        vm=new ViewModelProvider((ViewModelStoreOwner) getContext(),new CurrentUserProfileViewModelFactory(getContext(),userData)).get(CurrentUserProfileViewModel.class);

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