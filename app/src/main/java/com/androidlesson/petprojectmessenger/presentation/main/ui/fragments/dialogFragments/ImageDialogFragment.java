package com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.imageDialogFragmentViewModel.ImageDialogFragmentViewModel;
import com.bumptech.glide.Glide;

public class ImageDialogFragment extends DialogFragment {

    private String imageUri;

    private ImageView iv_image;

    public ImageDialogFragment(String imageUri) {
        this.imageUri = imageUri;
    }

    public ImageDialogFragment() {
    }

    private ImageDialogFragmentViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        vm=new ViewModelProvider(getActivity()).get(ImageDialogFragmentViewModel.class);

        if (imageUri!=null){
            vm.setImageUri(imageUri);
        }

        iv_image=view.findViewById(R.id.iv_image);
        if (vm.getImageUri()!=null && !vm.getImageUri().isEmpty()){
            Glide.with(getContext()).load(vm.getImageUri()).override(1000,1000).into(iv_image);
        }


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Dialog dialog = getDialog();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }
        }
    }
}

