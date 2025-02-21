package com.androidlesson.petprojectmessenger.presentation.main.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.AnotherUserProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final List<UserData> users = new ArrayList<>();
    private final Context context;
    private UserData currUser;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void updateCurrentUser(UserData userData){
        currUser=userData;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<UserData> newUsers) {
        if (newUsers == null || newUsers.isEmpty()) {
            users.clear();
        } else {
            users.clear();
            users.addAll(newUsers);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_rv, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserData user = users.get(position);
        holder.bind(user);

        holder.itemView.setOnClickListener(v -> {
            if (currUser == null) {
                Log.e("UserAdapter", "CURRENT_USER_DATA is null. Cannot open profile.");
                return;
            }
            Intent intent = new Intent(context, AnotherUserProfileActivity.class);
            intent.putExtra("ANOTHER_USER_DATA", user);
            intent.putExtra("CURRENT_USER_DATA", currUser);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_user_name_and_surname);
        }

        public void bind(UserData user) {
            nameTextView.setText(user.getUserName() + " " + user.getUserSurname());
        }
    }
}
