package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.databinding.FragmentMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.MainFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.MainFragmentViewModelFactory;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainFragmentViewModel vm;

    //User data from activity
    private UserData currUserData;
    public MainFragment(UserData currUserData) {
        this.currUserData = currUserData;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        initialization();

        return binding.getRoot();
    }

    private void initialization() {
        vm = new ViewModelProvider(this, new MainFragmentViewModelFactory()).get(MainFragmentViewModel.class);
        Log.d("AAA","MainFragment start");
    }

}