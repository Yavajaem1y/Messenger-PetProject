package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentAllUsersBinding;
import com.androidlesson.petprojectmessenger.presentation.main.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.adapter.UserAdapter;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.AllUsersFragmetViewModel.AllUsersFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.AllUsersFragmetViewModel.AllUsersFragmentViewModelFactory;

import javax.inject.Inject;

public class AllUsersFragment extends Fragment {

    private FragmentAllUsersBinding binding;

    private AllUsersFragmentViewModel vm;
    @Inject
    AllUsersFragmentViewModelFactory allUsersFragmentViewModelFactory;

    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private boolean isLoading = false;

    private UserData currUser;

    public static MainFragment newInstance(UserData userData) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("USERDATA",userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentAllUsersBinding.inflate(inflater, container, false);

        initialization();

        setObserver();

        setOnScrollListener();

        return binding.getRoot();
    }

    private void initialization(){
        ((App) requireActivity().getApplication()).appComponent.injectAllUsersFragment(this);
        vm= new ViewModelProvider(this,allUsersFragmentViewModelFactory).get(AllUsersFragmentViewModel.class);

        recyclerView=binding.rvUsers;

        if(getArguments()!=null){
            currUser=(UserData) getArguments().get("USERDATA");
        }

        userAdapter = new UserAdapter(getContext(),currUser);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.loadMoreUsers(10);
    }

    private void setObserver(){
        vm.getUsersLiveData().observe(getViewLifecycleOwner(), users -> {
            userAdapter.addUsers(users);
            isLoading = false;
        });
    }

    private void setOnScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null &&
                        layoutManager.findLastVisibleItemPosition() == userAdapter.getItemCount() - 1) {
                    isLoading = true;
                    vm.loadMoreUsers(10);
                }
            }
        });
    }
}