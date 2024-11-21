package com.androidlesson.petprojectmessenger.presentation.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.databinding.ActivityMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;

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
    }

    private void setObserver(){
        vm.getCurrentFragmentLiveData().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_main_activity_fragment_container,fragment).commit();
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