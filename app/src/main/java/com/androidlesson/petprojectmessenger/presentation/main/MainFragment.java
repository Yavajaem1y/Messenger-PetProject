package com.androidlesson.petprojectmessenger.presentation.main;

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
import com.androidlesson.petprojectmessenger.databinding.FragmentMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableCallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableUserData;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.MainFragmentViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainFragmentViewModel vm;

    private BottomNavigationView bottomNavigationView;

    private Fragment currFragment;

    //User data from activity
    private UserData currUserData;
    private CallbackLogOut callbackLogOut;


    public static MainFragment newInstance(SerializableUserData serializableUserData, SerializableCallbackLogOut serializableCallbackLogOut) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("USERDATA",serializableUserData);
        args.putSerializable("CALLBACK_LOG_OUT",serializableCallbackLogOut);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            currUserData=((SerializableUserData)getArguments().get("USERDATA")).getUserData();
            callbackLogOut=((SerializableCallbackLogOut)getArguments().get("CALLBACK_LOG_OUT")).getCallbackLogOut();
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
        vm = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);

        currFragment=vm.getMainFragmentSceneLiveData().getValue();
        if (currFragment!=null) getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, currFragment).commit();

        vm.setFragmentsInfo(currUserData,callbackLogOut);

        bottomNavigationView=binding.bnvMainBottomBar;
    }

    private void setObserver(){
        vm.getMainFragmentSceneLiveData().observe(getViewLifecycleOwner(), new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment!=null && (currFragment==null || currFragment!=fragment)) {
                    Log.d("AAA", "Refresh fragment in MainFragment to" + fragment);
                    currFragment=fragment;
                    getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, fragment).commit();
                }
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