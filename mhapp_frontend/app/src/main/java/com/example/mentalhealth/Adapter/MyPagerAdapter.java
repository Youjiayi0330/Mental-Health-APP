package com.example.mentalhealth.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mentalhealth.MyImageView.CircularBeadImageView;
import com.example.mentalhealth.R;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;

    public MyPagerAdapter(){}

    public MyPagerAdapter(ArrayList<View> viewLists){
        super();
        this.viewLists=viewLists;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % viewLists.size();
        View view=viewLists.get(position);
        ViewGroup parent=(ViewGroup) view.getParent();
        if(parent!=null)
            parent.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//        object = null;
    }

}
