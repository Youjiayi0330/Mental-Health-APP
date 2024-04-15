package com.example.mentalhealth.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mentalhealth.R;
import com.example.mentalhealth.entity.ImageListArray;

import java.util.List;

public class ImageListAdapter extends ArrayAdapter<ImageListArray> {
    private int recourceId;

    public ImageListAdapter(Context context, int resource, List<ImageListArray> objects){
        super(context,resource,objects);
        recourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ImageListArray imageListArray=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(recourceId,parent,false);
        ImageView imageView=view.findViewById(R.id.cover);
        TextView tv_ptitle=view.findViewById(R.id.ptitle);
        TextView tv_ptime=view.findViewById(R.id.ptime);
        TextView tv_collect_count = view.findViewById(R.id.collect_count);
        //Bitmap bitmap = getHttpBitmap("http://172.20.10.10:8081/popmh/cover/"+imageListArray.getFileName());
        String url="http://172.20.10.10:8081/popmh/cover/"+imageListArray.getFileName();
        //Bitmap bitmap=null;
        Log.d("url",url);
        //Bitmap bitmap = getHttpBitmap(url);
        //Log.d("Bitmap",bitmap.toString());
        Glide.with(view.getContext()).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        //imageView.setImageBitmap(bitmap);
        tv_ptitle.setText(imageListArray.getPtitle());
        tv_ptime.setText(imageListArray.getPtime());
        tv_collect_count.setText(imageListArray.getCollectCount().toString());
        return view;
    }

//    public static Bitmap getHttpBitmap(String url) {
//        Bitmap[] bitmap = {null};
//        new Thread(new Runnable() {
//            URL myFileUrl = null;
//
//            @Override
//            public void run() {
//                try {
//                    myFileUrl = new URL(url);
//                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//                    conn.setConnectTimeout(0);
//                    conn.setDoInput(true);
//                    conn.connect();
//                    InputStream is = conn.getInputStream();
//                    bitmap[0] = BitmapFactory.decodeStream(is);
//                    is.close();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
////                try {
////                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
////                    conn.setConnectTimeout(0);
////                    conn.setDoInput(true);
////                    conn.connect();
////                    InputStream is = conn.getInputStream();
////                    bitmap = BitmapFactory.decodeStream(is);
////                    bitmap1=bitmap;
////                    is.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
//        }).start();
//
//
//        return bitmap[0];
//    }

}
