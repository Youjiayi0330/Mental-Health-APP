package com.example.mentalhealth.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteImagePageAdapter extends PagerAdapter {
    private ArrayList<View> ivList;

    public NoteImagePageAdapter(){};

    public NoteImagePageAdapter(ArrayList<View> ivList) {
        this.ivList = ivList;
    }

    @Override
    public int getCount() {
        return ivList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = ivList.get(position);
        ViewGroup parent=(ViewGroup) view.getParent();
        if(parent!=null)
            parent.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //container.removeView(ivList.get(position));
    }

}
