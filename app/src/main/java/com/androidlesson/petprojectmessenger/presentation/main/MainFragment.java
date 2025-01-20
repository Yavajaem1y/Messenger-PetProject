package com.androidlesson.petprojectmessenger.presentation.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.MainFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.MainFragmentViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainFragmentViewModel vm;
    private SharedViewModel sharedVM;

    @Inject
    MainFragmentViewModelFactory mvFactory;
    @Inject
    SharedViewModelFactory svmFactory;

    private BottomNavigationView bottomNavigationView;

    private Fragment currFragment;

    //User data from activity
    private UserData currUserData;


    public static MainFragment newInstance(UserData userData) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("USERDATA",userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            currUserData=((UserData)getArguments().get("USERDATA"));
        }
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
        ((App) requireActivity().getApplication()).appComponent.injectMainFragment(this);

        vm = new ViewModelProvider(requireActivity(),mvFactory).get(MainFragmentViewModel.class);
        sharedVM=new ViewModelProvider(requireActivity(),svmFactory).get(SharedViewModel.class);

        sharedVM.loadUserData();

        currFragment=vm.getMainFragmentSceneLiveData().getValue();
        if (currFragment!=null) getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, currFragment).commit();

        vm.setFragmentsInfo();

        bottomNavigationView=binding.bnvMainBottomBar;
        bottomNavigationView.setItemIconTintList(null);
    }

    private void setObserver(){
        vm.getMainFragmentSceneLiveData().observe(getViewLifecycleOwner(), new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment!=null && (currFragment==null || currFragment!=fragment)) {
                    currFragment=fragment;
                    getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, fragment).commit();
                }
            }
        });

        sharedVM.getUserData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                vm.setUserData(userData);
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