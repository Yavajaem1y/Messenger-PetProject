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

import com.androidlesson.domain.main.models.ChatInfoForLoad;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.utils.FullDateToTime;
import com.androidlesson.petprojectmessenger.R;
import com.androidlesson.petprojectmessenger.presentation.main.ui.activity.AnotherUserProfileActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    Comparator<ChatInfoForLoad> chatComparator = new Comparator<ChatInfoForLoad>() {
        @Override
        public int compare(ChatInfoForLoad chat1, ChatInfoForLoad chat2) {
            String time1 = chat1.getTimeLastMessage();
            String time2 = chat2.getTimeLastMessage();

            return time2.compareTo(time1);
        }
    };

    private final Context context;
    private Set<ChatInfoForLoad> chatSet = new TreeSet<>(chatComparator);
    private final OnChatClickListener onChatClickListener;

    public interface OnChatClickListener {
        void onChatClick(ChatInfoForLoad chat);
    }

    public ChatsAdapter(OnChatClickListener listener,Context context) {
        this.onChatClickListener = listener;
        this.context=context;
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
        return new ChatViewHolder(view,context);
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
        private final CircleImageView civUserAvatar;
        private final Context context;

        public ChatViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context=context;

            userName = itemView.findViewById(R.id.tv_user_name_and_surname);
            lastMessage = itemView.findViewById(R.id.tv_text_last_message);
            timeLastMessage = itemView.findViewById(R.id.tv_time_of_last_message);
            civUserAvatar=itemView.findViewById(R.id.civ_user_avatar);
        }

        public void bind(ChatInfoForLoad chat, OnChatClickListener listener) {
            userName.setText(chat.getAnotherUserNameAndSurname());
            lastMessage.setText(chat.getTextLastMessage());
            FullDateToTime fullDateToTime=new FullDateToTime();
            timeLastMessage.setText(fullDateToTime.execute(chat.getTimeLastMessage()));
            if (chat.getAnotherUserAvatar()!=null && !chat.getAnotherUserAvatar().isEmpty()) {
                Glide.with(context).load(chat.getAnotherUserAvatar()).into(civUserAvatar);
            }

            itemView.setOnClickListener(v -> listener.onChatClick(chat));
        }
    }
}
