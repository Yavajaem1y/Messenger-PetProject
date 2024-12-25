package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.anotherActivity.AnotherUserProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final List<UserData> users = new ArrayList<>();
    int startPosition = 0;

    private Context context;
    private UserData currUser;

    public UserAdapter(Context context, UserData currUser) {
        this.context = context;
        this.currUser = currUser;
    }

    public void addUsers(List<UserData> newUsers) {
        for (UserData userData:newUsers){
            if (!users.contains(userData)){
                startPosition +=1;
                users.add(userData);
                notifyItemRangeInserted(startPosition, newUsers.size());
            }
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_rv, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserData user = users.get(position);
        holder.bind(user);

        holder.itemView.setOnClickListener(v -> {
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
