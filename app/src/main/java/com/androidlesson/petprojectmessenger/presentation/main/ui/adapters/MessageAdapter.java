package com.androidlesson.petprojectmessenger.presentation.main.ui.adapters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.utils.FullDateToTime;
import com.androidlesson.petprojectmessenger.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<ChatInfo.Message> messages = new ArrayList<>(); // Используем ArrayList вместо TreeSet
    private String currentUserId;

    public void setCurrentUser(UserData userData) {
        currentUserId = userData.getUserId();
    }

    @Override
    public int getItemViewType(int position) {
        ChatInfo.Message message = messages.get(position);

        return message.getSender().equals(currentUserId) ?
                R.layout.current_user_message_item_rv :
                R.layout.another_user_message_item_rv;
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

        holder.tvMessage.setText(message.getMessage());
        FullDateToTime fullDateToTime=new FullDateToTime();
        holder.tvTime.setText(fullDateToTime.execute(message.getTimeSending()));

        if (holder.tvUserName != null) {
            holder.tvUserName.setText(message.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessageToEnd(ChatInfo.Message message) {
        if (message == null) return;

        new Handler(Looper.getMainLooper()).post(() -> {
        });
    }

    public void addMessagesToStart(List<ChatInfo.Message> newMessages) {
        if (newMessages == null || newMessages.isEmpty()) {
            Log.d("MessageAdapter", "addMessagesToStart - no new messages to add");
            return;
        }

        new Handler(Looper.getMainLooper()).post(() -> {
            for (ChatInfo.Message message:newMessages){
                if (!messages.contains(message)){
                    messages.add(message);
                }
            }
            messages.sort(Comparator.comparing(ChatInfo.Message::getTimeSending));
            notifyDataSetChanged();
        });
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime, tvUserName;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUserName = itemView.findViewById(R.id.tv_user_name_and_surname);
        }
    }
}
