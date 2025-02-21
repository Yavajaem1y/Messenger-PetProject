package com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.bottomFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.ChatInfoForLoad;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.app.App;
import com.androidlesson.petprojectmessenger.databinding.FragmentAllUserChatsBinding;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.AnotherUserProfileActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.ChatWithUserActivity;
import com.androidlesson.petprojectmessenger.presentation.main.ui.adapters.ChatsAdapter;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllChatsFragmentViewModel.AllChatsFragmentViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllChatsFragmentViewModel.AllChatsFragmentViewModelFactory;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class AllChatsFragment extends Fragment {
    private FragmentAllUserChatsBinding binding;

    private SharedViewModel sharedViewModel;
    private AllChatsFragmentViewModel vm;

    private ChatsAdapter chatsAdapter;
    private RecyclerView recyclerView;

    @Inject
    SharedViewModelFactory sharedViewModelFactory;
    @Inject
    AllChatsFragmentViewModelFactory allChatsFragmentViewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentAllUserChatsBinding.inflate(inflater, container, false);

        initialization();

        observer();

        return binding.getRoot();
    }

    private void initialization(){
        ((App) requireActivity().getApplication()).appComponent.injectAllChatsFragment(this);
        sharedViewModel= new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);
        vm=new ViewModelProvider(this,allChatsFragmentViewModelFactory).get(AllChatsFragmentViewModel.class);

        recyclerView = binding.rvAllChats;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatsAdapter = new ChatsAdapter(chat -> {
            Intent intent = new Intent(getContext(), ChatWithUserActivity.class);
            intent.putExtra("CHAT_ID", chat.getChatId());
            getContext().startActivity(intent);
        },getContext());

        recyclerView.setAdapter(chatsAdapter);
    }

    private void observer(){
        sharedViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    vm.setCurrentUserLiveData(userData);
                }
            }
        });


        vm.getChatsLiveData().observe(getViewLifecycleOwner(), new Observer<List<ChatInfoForLoad>>() {
            @Override
            public void onChanged(List<ChatInfoForLoad> chatInfoForLoads) {
                chatsAdapter.setChatList(chatInfoForLoads);
            }
        });

    }


}