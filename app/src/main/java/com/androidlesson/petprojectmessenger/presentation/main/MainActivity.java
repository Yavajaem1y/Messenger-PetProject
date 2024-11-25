package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.databinding.ActivityMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;

    private Fragment currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        setObserver();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init(){
        vm = new ViewModelProvider(this, new MainActivityViewModelFactory(getApplicationContext())).get(MainActivityViewModel.class);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        currFragment=vm.getCurrentFragmentLiveData().getValue();
        if (currFragment!=null) fragmentTransaction.replace(R.id.fl_main_activity_fragment_container,currFragment).commit();
    }

    private void setObserver(){
        vm.getCurrentFragmentLiveData().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment!=null && (currFragment==null || currFragment!=fragment)){
                    Log.d("AAA","Refresh fragment in MainActivity to "+fragment);
                    currFragment=fragment;
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main_activity_fragment_container,fragment).commit();
                }
            }
        });

        vm.newUserDataHasBeenReceivedLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==2){
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    currFragment=vm.getCurrentFragmentLiveData().getValue();
                    if (currFragment!=null) fragmentTransaction.replace(R.id.fl_main_activity_fragment_container,currFragment).commit();
                }
            }
        });

        vm.logOutLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) finish();
            }
        });
    }

}