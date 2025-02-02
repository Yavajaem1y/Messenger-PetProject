package com.androidlesson.petprojectmessenger.presentation.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.ActivityMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel.MainActivityViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements OnDataPass{

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;
    private SharedViewModel sharedViewModel;

    private Fragment currFragment;

    @Inject
    MainActivityViewModelFactory mainActivityVMFactory;

    @Inject
    SharedViewModelFactory sharedViewModelFactory;

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
        ((App) getApplication()).appComponent.injectMainActivity(this);
        vm = new ViewModelProvider(this,mainActivityVMFactory).get(MainActivityViewModel.class);
        sharedViewModel=new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);

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
    }

    @Override
    public void onDataPass(String data) {
        vm.logOut();
        finish();
    }



    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        sharedViewModel.setFirstFragment(true);
    }

}