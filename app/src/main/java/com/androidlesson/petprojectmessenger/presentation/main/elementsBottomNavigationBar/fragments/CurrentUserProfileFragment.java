package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentCurrentUserProfileBinding;
import com.androidlesson.petprojectmessenger.presentation.main.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.OnDataPass;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileFragmentViewModel.CurrentUserProfileViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;


import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUserProfileFragment extends Fragment {

    private FragmentCurrentUserProfileBinding binding;
    private CurrentUserProfileViewModel vm;

    private SharedViewModel sharedViewModel;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private ImageView iv_logout;
    private TextView tv_name_and_surname,tv_user_id;
    private CircleImageView ciw_profile_avatar;
    private ScrollView sv_main;
    private RelativeLayout rl_bottom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentCurrentUserProfileBinding.inflate(inflater, container, false);

        init();

        addOnScrollListener();

        setObserver();

        setOnClickListener();

        return binding.getRoot();
    }

    private void init() {
        ((App) requireActivity().getApplication()).appComponent.injectCurrentUserProfileFragment(this);

        vm=new ViewModelProvider(requireActivity()).get(CurrentUserProfileViewModel.class);
        sharedViewModel=new ViewModelProvider(requireActivity(),sharedViewModelFactory).get(SharedViewModel.class);

        vm.setVMInfo(sharedViewModel.getUserData().getValue());

        iv_logout=binding.ivLogout;
        tv_name_and_surname=binding.tvCurrUserNameAndSurname;
        ciw_profile_avatar=binding.civCurrUserAvatar;
        sv_main=binding.svMainLayout;
        rl_bottom=binding.rlBottomLayout;
        tv_user_id=binding.tvCurrnetUserId;

        rl_bottom.setVisibility(View.GONE);

    }

    private void addOnScrollListener(){
        sv_main.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = sv_main.getScrollY();

            if (scrollY > 0) { vm.setVisibilityTopElement(1);
            } else {
                vm.setVisibilityTopElement(0);
            }
        });
    }

    private void setObserver(){
        vm.getUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                tv_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
                tv_user_id.setText("@"+userData.getUserId());
            }
        });

        vm.getVisibilityTopElementLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    rl_bottom.animate().alpha(1f).setDuration(300).start();
                    rl_bottom.setVisibility(View.VISIBLE);
                    iv_logout.setColorFilter(getResources().getColor(R.color.white));
                }
                else {
                    rl_bottom.animate().alpha(0f).setDuration(300).start();
                    rl_bottom.setVisibility(View.GONE);
                    iv_logout.setColorFilter(getResources().getColor(R.color.accent_color));
                }
            }
        });

        sharedViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                vm.setVMInfo(userData);
            }
        });
    }
    OnDataPass onDataPass;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        onDataPass=(OnDataPass) activity;
    }

    private void setOnClickListener(){
        iv_logout.setOnClickListener(v->{
            onDataPass.onDataPass("sda");
        });
    }
}