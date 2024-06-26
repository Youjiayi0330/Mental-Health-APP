package com.example.mentalhealth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentalhealth.R;

import java.io.File;
import java.util.ArrayList;

public class ImageSelectorAdapter extends RecyclerView.Adapter<ImageSelectorAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mImages;
    private LayoutInflater mInflater;

    public ImageSelectorAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String image = mImages.get(position);
        Glide.with(mContext).load(new File(image)).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

}
