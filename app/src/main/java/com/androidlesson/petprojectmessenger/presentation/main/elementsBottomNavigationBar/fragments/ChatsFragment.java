package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.petprojectmessenger.databinding.FragmentAllUserChatsBinding;

public class ChatsFragment extends Fragment {
    private FragmentAllUserChatsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentAllUserChatsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}