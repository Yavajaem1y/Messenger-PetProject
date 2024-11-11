package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.petprojectmessenger.databinding.FragmentMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.MainFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.MainFragmentViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainFragmentViewModel mainFragmentViewModel;
    private CurrentUserDataFromDB currentUserDataFromDB;

    public MainFragment(CurrentUserDataFromDB currentUserDataFromDB) {
        this.currentUserDataFromDB = currentUserDataFromDB;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        initialization();
        selectFragment();
        setObserve();

        return binding.getRoot();
    }

    private void initialization() {
        mainFragmentViewModel = new ViewModelProvider(this, new MainFragmentViewModelFactory(currentUserDataFromDB)).get(MainFragmentViewModel.class);
    }

    private void selectFragment() {
        binding.bnvMainBottomBar.setOnItemSelectedListener(item -> {
            mainFragmentViewModel.selectFragment(item.getItemId());
            return true;
        });
    }

    private void setObserve() {
        mainFragmentViewModel.selectedFragmentLiveData.observe(getViewLifecycleOwner(), fragment -> {
            getParentFragmentManager().beginTransaction().replace(binding.flMainFragmentContainer.getId(), fragment).commit();
        });
    }
}