package com.androidlesson.petprojectmessenger.presentation.main.elementsBottomNavigationBar.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Set<ChatInfo.Message> messages = new TreeSet<>((m1, m2) -> m1.getTimeSending().compareTo(m2.getTimeSending())); // Сортировка по времени
    private String currentUserId;

    public void setCurrentUser(UserData userData) {
        currentUserId = userData.getUserId();
    }

    @Override
    public int getItemViewType(int position) {
        List<ChatInfo.Message> sortedMessages = new ArrayList<>(messages); // Преобразуем в список для получения по индексу
        ChatInfo.Message message = sortedMessages.get(position);
        if (message.getSender().equals(currentUserId)) {
            return R.layout.current_user_message_item_rv;
        } else {
            return R.layout.another_user_message_item_rv;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        List<ChatInfo.Message> sortedMessages = new ArrayList<>(messages); // Преобразуем в список для получения по индексу
        ChatInfo.Message message = sortedMessages.get(position);
        holder.tvMessage.setText(message.getMessage());
        holder.tvTime.setText(message.getTimeSending());

        if (holder.tvUserName != null) {
            holder.tvUserName.setText(message.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Метод для добавления одного сообщения в конец списка
    public void addMessageToEnd(ChatInfo.Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    // Метод для добавления нескольких сообщений в начало списка
    public void addMessagesToStart(List<ChatInfo.Message> newMessages) {
        if (newMessages == null || newMessages.isEmpty()) return;

        List<ChatInfo.Message> uniqueMessages = new ArrayList<>();
        for (ChatInfo.Message message : newMessages) {
            if (!messages.contains(message)) {
                uniqueMessages.add(message);
            }
        }

        // Добавляем уникальные сообщения в TreeSet, они будут отсортированы автоматически
        messages.addAll(uniqueMessages);

        // Уведомляем адаптер, что добавлены новые элементы в начало
        notifyItemRangeInserted(0, uniqueMessages.size());
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
