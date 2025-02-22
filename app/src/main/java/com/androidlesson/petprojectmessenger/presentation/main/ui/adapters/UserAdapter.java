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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final List<UserData> users = new ArrayList<>();
    private final Context context;
    private UserData currUser;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_user, parent, false);
        return new UserViewHolder(view,context);
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
        private final CircleImageView civAvatar;

        private Context context;

        public UserViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context=context;

            nameTextView = itemView.findViewById(R.id.tv_user_name_and_surname);
            civAvatar=itemView.findViewById(R.id.civ_user_avatar);
        }

        public void bind(UserData user) {
            nameTextView.setText(user.getUserName() + " " + user.getUserSurname());
            if (user.getImageData()!=null && !user.getImageData().isEmpty()){
                Glide.with(context).load(user.getImageData()).into(civAvatar);
            }
        }
    }
}
