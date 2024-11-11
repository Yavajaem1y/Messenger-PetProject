package com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.petprojectmessenger.databinding.FragmentCurrentUserProfileBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserAvatarFromMainVM;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel.CurrentUserProfileViewModelFactory;
import com.bumptech.glide.Glide;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUserProfileFragment extends Fragment {

    private FragmentCurrentUserProfileBinding binding;
    private CurrentUserProfileViewModel vm;

    private ImageView iv_logout;
    private TextView tv_name_and_surname;
    private CircleImageView ciw_profile_avatar,ciw_add_profile_photo;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private CurrentUserAvatarFromMainVM currentUserAvatarFromMainVM;
    private CurrentUserDataFromDB currentUserDataFromDB;

    public CurrentUserProfileFragment(CurrentUserAvatarFromMainVM currentUserAvatarFromMainVM, CurrentUserDataFromDB currentUserDataFromDB) {
        this.currentUserAvatarFromMainVM = currentUserAvatarFromMainVM;
        this.currentUserDataFromDB = currentUserDataFromDB;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentCurrentUserProfileBinding.inflate(inflater, container, false);

        init();

        setObserve();

        onClickListener();

        return binding.getRoot();
    }

    private void init() {
        vm=new ViewModelProvider((ViewModelStoreOwner) getContext(),new CurrentUserProfileViewModelFactory(getContext(),
                currentUserAvatarFromMainVM,
                currentUserDataFromDB)).get(CurrentUserProfileViewModel.class);

        iv_logout=binding.ivLogout;
        tv_name_and_surname=binding.tvCurrUserNameAndSurname;
        ciw_profile_avatar=binding.civCurrUserAvatar;
        ciw_add_profile_photo=binding.ciwAddProfilePhoto;
    }

    private void setObserve(){
        vm.userAvatarMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] image) {
                if (image!=null) Glide.with(getContext()).load(image).into(ciw_profile_avatar);
            }
        });

        vm.userNameAndSurnameMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<UserNameAndSurname>() {
            @Override
            public void onChanged(UserNameAndSurname user) {
                tv_name_and_surname.setText(user.getUserName()+" "+user.getUserSurname());
            }
        });
    }

    private void onClickListener(){
        iv_logout.setOnClickListener(v->{
            vm.logOut();
            getActivity().onBackPressed();
        });

        ciw_add_profile_photo.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                ByteArrayOutputStream
                outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer,
                            0, length);
                }
                vm.setAvatarImage(outputStream.toByteArray());


                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}