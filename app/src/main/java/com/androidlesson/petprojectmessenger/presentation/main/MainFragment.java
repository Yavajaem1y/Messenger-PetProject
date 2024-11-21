package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.databinding.FragmentMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.MainFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.MainFragmentViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainFragmentViewModel vm;

    private BottomNavigationView bottomNavigationView;

    //User data from activity
    private UserData currUserData;
    private CallbackLogOut callbackLogOut;
    public MainFragment(UserData currUserData, CallbackLogOut callbackLogOut) {
        this.currUserData = currUserData;
        this.callbackLogOut=callbackLogOut;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        initialization();

        setObserver();

        setOnItemClicker();

        return binding.getRoot();
    }

    private void initialization() {
        vm = new ViewModelProvider(this, new MainFragmentViewModelFactory(currUserData,callbackLogOut)).get(MainFragmentViewModel.class);

        bottomNavigationView=binding.bnvMainBottomBar;
    }

    private void setObserver(){
        vm.getMainFragmentSceneLiveData().observe(getViewLifecycleOwner(), new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container,fragment).commit();

            }
        });
    }

    private void setOnItemClicker(){
        bottomNavigationView.setOnItemSelectedListener(item->{
            vm.replaceFragment(item.getItemId());
            return false;
        });
    }

}