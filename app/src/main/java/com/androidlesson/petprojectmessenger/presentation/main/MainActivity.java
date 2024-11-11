package com.androidlesson.petprojectmessenger.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.databinding.ActivityMainBinding;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setObserve();
    }

    private void init(){
        mainActivityViewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(getApplicationContext())).get(MainActivityViewModel.class);
    }


    private void setObserve(){
        mainActivityViewModel.getCurrentUserDataSceneLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    getSupportFragmentManager().beginTransaction().replace(binding.flMainActivityFragmentContainer.getId(), new SetCurrentUserDataFragment(mainActivityViewModel)).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().replace(binding.flMainActivityFragmentContainer.getId(), new MainFragment(mainActivityViewModel.getCurrentUserDataFromDB())).commit();
                }
            }
        });
    }
}