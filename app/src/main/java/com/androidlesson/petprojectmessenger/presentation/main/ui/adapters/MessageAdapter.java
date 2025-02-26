package com.androidlesson.petprojectmessenger.presentation.main.ui.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.utils.FullDateToTime;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments.ImageDialogFragment;
import com.androidlesson.petprojectmessenger.presentation.main.ui.fragments.dialogFragments.MoreInfoDialogFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<ChatInfo.Message> messages = new ArrayList<>();
    private String currentUserId;
    private Context context;
    private FragmentManager fragmentManager;

    public MessageAdapter(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public void setCurrentUser(UserData userData) {
        currentUserId = userData.getUserId();
    }

    @Override
    public int getItemViewType(int position) {
        ChatInfo.Message message = messages.get(position);

        return message.getSender().equals(currentUserId) ?
                R.layout.item_rv_current_user_message :
                R.layout.item_rv_another_user_message;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatInfo.Message message = messages.get(position);

        if (message.getMessage() != null && !message.getMessage().isEmpty()) {
            holder.tvMessage.setText(message.getMessage());
            holder.tvMessage.setVisibility(View.VISIBLE);
        } else {
            holder.tvMessage.setVisibility(View.GONE);
        }

        FullDateToTime fullDateToTime = new FullDateToTime();
        holder.tvTime.setText(fullDateToTime.execute(message.getTimeSending()));

        if (holder.tvUserName != null) {
            holder.tvUserName.setText(message.getSender());
        }

        String imageUri = message.getImageUri();

        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(context)
                    .load(imageUri)
                    .override(600, 600)
                    .into(holder.iv_image);
            holder.iv_image.setOnClickListener(v->{
                ImageDialogFragment dialogFragment = new ImageDialogFragment(imageUri);
                dialogFragment.show(fragmentManager, "my_dialog");
            });
        } else {
            holder.iv_image.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessagesToStart(List<ChatInfo.Message> newMessages) {
        for (ChatInfo.Message newMessage : newMessages) {
            boolean exists = false;
            for (ChatInfo.Message existingMessage : messages) {
                if (existingMessage.getTimeSending().equals(newMessage.getTimeSending())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                messages.add(0, newMessage);
            }
        }
        messages.sort(Comparator.comparing(ChatInfo.Message::getTimeSending));
        notifyDataSetChanged();
    }


    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime, tvUserName;
        ImageView iv_image;
        RelativeLayout rl_main;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUserName = itemView.findViewById(R.id.tv_user_name_and_surname);
            iv_image=itemView.findViewById(R.id.iv_image);
            rl_main=itemView.findViewById(R.id.rl_main_layout);
        }
    }
}
