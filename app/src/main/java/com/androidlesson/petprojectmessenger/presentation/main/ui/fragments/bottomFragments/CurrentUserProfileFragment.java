package com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments.DotsMenuFragmentFromCurrnetUserActivity;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentCurrentUserProfileBinding;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments.MoreInfoDialogFragment;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.CurrentUserProfileFragmentViewModel.CurrentUserProfileViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.CurrentUserProfileFragmentViewModel.CurrentUserProfileViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUserProfileFragment extends Fragment {

    private FragmentCurrentUserProfileBinding binding;
    private CurrentUserProfileViewModel vm;

    private SharedViewModel sharedViewModel;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;
    @Inject
    CurrentUserProfileViewModelFactory currentUserProfileViewModelFactory;

    private ImageView iv_dots_menu;
    private TextView tv_name_and_surname,tv_user_id,tv_number_of_friends,tv_more_info;
    private CircleImageView ciw_profile_avatar;
    private ScrollView sv_main;
    private RelativeLayout rl_bottom;

    //User data from fragment
    private UserData currUserData;
    public static MainFragment newInstance(UserData userData) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("USERDATA",userData);
        fragment.setArguments(args);
        return fragment;
    }

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currUserData = (UserData) getArguments().get("USERDATA");
        }
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();

                        try {
                            Context context = getContext();
                            if (context == null) return; // Проверяем, что контекст не null

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                            byte[] imageData = convertBitmapToByteArray(bitmap);

                            vm.uploadImageAvatar(imageData);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
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

        vm=new ViewModelProvider(requireActivity(),currentUserProfileViewModelFactory).get(CurrentUserProfileViewModel.class);
        sharedViewModel=new ViewModelProvider(requireActivity(),sharedViewModelFactory).get(SharedViewModel.class);

        vm.setVMInfo(currUserData);

        iv_dots_menu =binding.ivDotsMenu;
        tv_name_and_surname=binding.tvCurrUserNameAndSurname;
        ciw_profile_avatar=binding.civCurrUserAvatar;
        sv_main=binding.svMainLayout;
        rl_bottom=binding.rlBottomLayout;
        tv_user_id=binding.tvCurrnetUserId;
        tv_number_of_friends=binding.tvNumbersOfFriends;
        tv_more_info=binding.tvMoreDetailed;

        rl_bottom.setVisibility(View.GONE);

        if (sharedViewModel.getCurrentUserAvatarLiveData().getValue()!=null){
            Glide.with(getContext()).load(sharedViewModel.getCurrentUserAvatarLiveData().getValue()).into(ciw_profile_avatar);
        }

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
                tv_number_of_friends.setText(userData.getFriendsIds().size() + " " +((userData.getFriendsIds().size()>1) ? "friends" : "friend"));
            }
        });

        vm.getVisibilityTopElementLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    rl_bottom.animate().alpha(1f).setDuration(300).start();
                    rl_bottom.setVisibility(View.VISIBLE);
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.white));
                }
                else {
                    rl_bottom.animate().alpha(0f).setDuration(300).start();
                    rl_bottom.setVisibility(View.GONE);
                    iv_dots_menu.setColorFilter(getResources().getColor(R.color.accent_color));
                }
            }
        });

        sharedViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null) {
                    Log.d("CurrentUserProfile", "User data received: " + userData.getUserName());
                    vm.setVMInfo(userData);
                } else {
                    Log.d("CurrentUserProfile", "User data is null");
                }
            }
        });

        sharedViewModel.getCurrentUserAvatarLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String imageUri) {
                Glide.with(getContext()).load(imageUri).into(ciw_profile_avatar);
            }
        });
    }

    private void setOnClickListener(){
        iv_dots_menu.setOnClickListener(v->{
            FragmentManager fragmentManager = getChildFragmentManager();
            DotsMenuFragmentFromCurrnetUserActivity dialogFragment = new DotsMenuFragmentFromCurrnetUserActivity();
            dialogFragment.show(fragmentManager, "my_dialog");
        });

        ciw_profile_avatar.setOnClickListener(v->{
            pickImageFromGallery();
        });

        tv_more_info.setOnClickListener(v->{
                FragmentManager fragmentManager = getParentFragmentManager();
                MoreInfoDialogFragment dialogFragment = new MoreInfoDialogFragment(vm.getUserDataLiveData().getValue().getUserInfo());
                dialogFragment.show(fragmentManager, "my_dialog");

        });
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }


}