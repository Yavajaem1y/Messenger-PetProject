package com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.interfaces.OnDataPass;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.EditUserDataActivity;

public class DotsMenuFragmentFromCurrnetUserActivity extends DialogFragment {

    private OnDataPass onDataPass;

    private LinearLayout ll_edit,ll_logout;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        onDataPass=(OnDataPass) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_dots_menu_from_current_user_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initialization(view);

        setOnClickListener();

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

                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.TOP | Gravity.END;
                params.x = 20;
                params.y = 150;
                window.setAttributes(params);
            }
        }
    }

    private void initialization(View view){
        ll_edit=view.findViewById(R.id.ll_edit_your_data);
        ll_logout=view.findViewById(R.id.ll_logout);
    }

    private void setOnClickListener(){
        ll_logout.setOnClickListener(v->{
            onDataPass.onDataPass("sda");
        });

        ll_edit.setOnClickListener(v->{
            startActivity(new Intent(getContext(), EditUserDataActivity.class));
        });
    }
}

