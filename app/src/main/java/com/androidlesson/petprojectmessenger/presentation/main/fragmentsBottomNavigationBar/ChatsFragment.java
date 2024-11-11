package com.androidlesson.petprojectmessenger.presentation.main.fragmentsBottomNavigationBar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.petprojectmessenger.databinding.FragmentChatWithUserBinding;

public class ChatsFragment extends Fragment {

    private FragmentChatWithUserBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentChatWithUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}