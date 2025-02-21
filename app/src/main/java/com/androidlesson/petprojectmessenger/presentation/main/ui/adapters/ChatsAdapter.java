package com.androidlesson.petprojectmessenger.presentation.main.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.ChatInfoForLoad;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.utils.FullDateToTime;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.AnotherUserProfileActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    Comparator<ChatInfoForLoad> chatComparator = new Comparator<ChatInfoForLoad>() {
        @Override
        public int compare(ChatInfoForLoad chat1, ChatInfoForLoad chat2) {
            String time1 = chat1.getTimeLastMessage();
            String time2 = chat2.getTimeLastMessage();

            return time2.compareTo(time1);
        }
    };


    private Set<ChatInfoForLoad> chatSet = new TreeSet<>(chatComparator);
    private final OnChatClickListener onChatClickListener;

    public interface OnChatClickListener {
        void onChatClick(ChatInfoForLoad chat);
    }

    public ChatsAdapter(OnChatClickListener listener) {
        this.onChatClickListener = listener;
    }

    // Обновленный метод для установки чатов
    @SuppressLint("NotifyDataSetChanged")
    public void setChatList(List<ChatInfoForLoad> chats) {
        chatSet.clear();
        chatSet.addAll(chats);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatInfoForLoad chat = (ChatInfoForLoad) chatSet.toArray()[position];
        holder.bind(chat, onChatClickListener);
    }

    @Override
    public int getItemCount() {
        return chatSet.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView lastMessage;
        private final TextView timeLastMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_user_name_and_surname);
            lastMessage = itemView.findViewById(R.id.tv_text_last_message);
            timeLastMessage = itemView.findViewById(R.id.tv_time_of_last_message);
        }

        public void bind(ChatInfoForLoad chat, OnChatClickListener listener) {
            userName.setText(chat.getAnotherUserNameAndSurname());
            lastMessage.setText(chat.getTextLastMessage());
            FullDateToTime fullDateToTime=new FullDateToTime();
            timeLastMessage.setText(fullDateToTime.execute(chat.getTimeLastMessage()));

            itemView.setOnClickListener(v -> listener.onChatClick(chat));
        }
    }
}
