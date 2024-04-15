package com.example.mentalhealth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.netty.pojo.ChatRecord;
import com.example.mentalhealth.netty.pojo.Person;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Person friend;
    private List<ChatRecord> data;
    private Context context;

    public ChatMessageAdapter(Context context, Person friend, List<ChatRecord> data){
        this.friend = friend;
        this.context = context;
        this.data = data;
    }

    public Person getFriend() {
        return friend;
    }

    public void setFriend(Person friend) {
        this.friend = friend;
    }

    public List<ChatRecord> getData() {
        return data;
    }

    public void setData(List<ChatRecord> data) {
        this.data = data;
    }

    //对方消息holder
    static class LeftChatMessageHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView message;
        public LeftChatMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message_left_message);
        }
    }

    //我的消息holder
    static class RightChatMessageHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView message;
        public RightChatMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message_right_message);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(data.get(position).getPersonId().equals(AppInfo.personNow.getId()))
            //我发送的消息
            return 0;
        else
            //对方发送的消息
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new RightChatMessageHolder(LayoutInflater.from(context).inflate(R.layout.chat_bubble0, parent, false));
        } else {
            return new LeftChatMessageHolder(LayoutInflater.from(context).inflate(R.layout.chat_bubble1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatRecord record = data.get(position);
        if(holder instanceof LeftChatMessageHolder){
            LeftChatMessageHolder h = (LeftChatMessageHolder) holder;
            h.message.setText(record.getMessage());
        }else if(holder instanceof RightChatMessageHolder){
            RightChatMessageHolder h = (RightChatMessageHolder) holder;
            h.message.setText(record.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }
}
