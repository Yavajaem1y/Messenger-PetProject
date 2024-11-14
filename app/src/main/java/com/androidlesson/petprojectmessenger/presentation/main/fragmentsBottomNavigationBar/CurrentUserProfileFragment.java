package com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlesson.petprojectmessenger.databinding.FragmentCurrentUserProfileBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModelFactory;


import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUserProfileFragment extends Fragment {

    private FragmentCurrentUserProfileBinding binding;
    private CurrentUserProfileViewModel vm;

    private ImageView iv_logout;
    private TextView tv_name_and_surname;
    private CircleImageView ciw_profile_avatar,ciw_add_profile_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentCurrentUserProfileBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {
        vm=new ViewModelProvider((ViewModelStoreOwner) getContext(),new CurrentUserProfileViewModelFactory(getContext())).get(CurrentUserProfileViewModel.class);

        iv_logout=binding.ivLogout;
        tv_name_and_surname=binding.tvCurrUserNameAndSurname;
        ciw_profile_avatar=binding.civCurrUserAvatar;
        ciw_add_profile_photo=binding.ciwAddProfilePhoto;
    }
}