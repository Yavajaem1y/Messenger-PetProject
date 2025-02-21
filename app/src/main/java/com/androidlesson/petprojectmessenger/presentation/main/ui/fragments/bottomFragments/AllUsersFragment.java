package com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentAllUsersBinding;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.adapters.UserAdapter;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllUsersFragmetViewModel.AllUsersFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllUsersFragmetViewModel.AllUsersFragmentViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class AllUsersFragment extends Fragment {

    private FragmentAllUsersBinding binding;

    private AllUsersFragmentViewModel vm;
    private SharedViewModel sharedViewModel;

    @Inject
    AllUsersFragmentViewModelFactory allUsersFragmentViewModelFactory;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;
    private RelativeLayout rl_all_users_filter, rl_my_friends_filter;
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private boolean isLoading = false;

    //User data from fragment
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
            currUserData=(UserData) getArguments().get("USERDATA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentAllUsersBinding.inflate(inflater, container, false);

        initialization();

        setOnClickListener();

        setObserver();

        setOnScrollListener();

        return binding.getRoot();
    }

    private void initialization(){
        ((App) requireActivity().getApplication()).appComponent.injectAllUsersFragment(this);
        vm= new ViewModelProvider(this,allUsersFragmentViewModelFactory).get(AllUsersFragmentViewModel.class);
        sharedViewModel=new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);


        recyclerView=binding.rvUsers;
        rl_all_users_filter=binding.rlAllUsersFilter;
        rl_my_friends_filter=binding.rlMyFriendsFilter;

        if(sharedViewModel.getUserData().getValue()!=null && sharedViewModel.getUserData().getValue().getUserId()!=null) currUserData=sharedViewModel.getUserData().getValue();
        vm.setCurrUser(currUserData);

        vm.setFilerFriends(false);
        userAdapter = new UserAdapter(requireContext());
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.loadMoreUsers(10);
    }

    private void setObserver() {
        vm.getUsersLiveData().observe(getViewLifecycleOwner(), users -> {
            userAdapter.updateData(users);
            isLoading = false;
        });

        sharedViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null && userData!=currUserData) {
                    currUserData=userData;
                    vm.setCurrUser(userData);
                    userAdapter.updateCurrentUser(userData);
                }
            }
        });
    }

    private void setOnClickListener(){
        rl_all_users_filter.setOnClickListener(v -> {
            vm.setFilerFriends(false);
            binding.tvTextAllUsersFilter.setTextColor(Color.parseColor("#D3E4B7"));
            binding.tvTextMyFriendsFilter.setTextColor(Color.parseColor("#FFFFFFFF"));
        });

        rl_my_friends_filter.setOnClickListener(v -> {
            vm.setFilerFriends(true);
            binding.tvTextMyFriendsFilter.setTextColor(Color.parseColor("#D3E4B7"));
            binding.tvTextAllUsersFilter.setTextColor(Color.parseColor("#FFFFFFFF"));
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