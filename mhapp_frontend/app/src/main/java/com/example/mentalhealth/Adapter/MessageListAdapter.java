package com.example.mentalhealth.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.netty.pojo.ChatListVo;
import com.example.mentalhealth.util.DateUtils;

import java.util.Date;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListVoHolder> {
    public List<ChatListVo> data;
    public Context context;
    public OnRecyclerItemListener mlistener = null;

    public MessageListAdapter(Context context, List<ChatListVo> data) {
        this.data=data;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageListVoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageListVoHolder(LayoutInflater.from(context).inflate(R.layout.message_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListVoHolder holder, int position) {
        //设置是否已读
        if(data.get(position).getHasRead() == 1){
            holder.hasRead.setVisibility(View.INVISIBLE);
        } else {
            holder.hasRead.setVisibility(View.VISIBLE);
        }
        //设置用户名
        holder.personName.setText(data.get(position).getPersonName());
        //设置时间
        Date createTime = data.get(position).getCreateTime();
        holder.time.setText(DateUtils.getMessageTime(createTime));
        //设置消息
        holder.message.setText(data.get(position).getMessage());
        //存用户id
        holder.personId = data.get(position).getPersonId();
        //设置监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlistener != null){
                    mlistener.onRecyclerItemClick(data.get(holder.getAdapterPosition()),holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    static final class MessageListVoHolder extends RecyclerView.ViewHolder{
        private final ImageView hasRead;
        private final TextView personName, time, message;
        private String personId;
        public MessageListVoHolder(@NonNull View itemView) {
            super(itemView);
            hasRead = itemView.findViewById(R.id.message_item_hasRead);
            personName = itemView.findViewById(R.id.message_item_name);
            time = itemView.findViewById(R.id.message_item_time);
            message = itemView.findViewById(R.id.message_item_message);
        }
    }

    public void setMlistener(OnRecyclerItemListener listener){
        mlistener = listener;
    }

    public interface OnRecyclerItemListener{
        void onRecyclerItemClick(ChatListVo data, int position);
    }
}
