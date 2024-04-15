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
import com.example.mentalhealth.entity.NoteComment;

import java.util.List;

public class NoteCommentAdapter extends RecyclerView.Adapter<NoteCommentAdapter.ViewHolder> {
    private Context mContext;
    private List<NoteComment> data;
    private LayoutInflater mInflator;
    private static View view;

    public NoteCommentAdapter(Context context, List<NoteComment> data) {
        this.mContext = context;
        this.data = data;
        this.mInflator = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflator.inflate(R.layout.commnet_lv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteComment noteComment = data.get(position);
        holder.tv_time.setText(noteComment.getTime());
        holder.tv_userName.setText(noteComment.getUserName());
        holder.tv_content.setText(noteComment.getContent());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void refresh(List<NoteComment> noteCommentList){
        this.data = noteCommentList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_time, tv_userName, tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.comment_time);
            tv_userName = itemView.findViewById(R.id.comment_userName);
            tv_content = itemView.findViewById(R.id.comment_content);
        }
    }
}
