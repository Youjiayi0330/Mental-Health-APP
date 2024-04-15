package com.example.mentalhealth.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mentalhealth.MyNote;
import com.example.mentalhealth.R;
import com.example.mentalhealth.UserDetail;
import com.example.mentalhealth.entity.ImageListArray;
import com.example.mentalhealth.entity.NoteListArray;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyGridViewAdapter extends ArrayAdapter<NoteListArray> {

    private int recourceId;
    private boolean isPublisher;

    public MyGridViewAdapter(Context context, int resource, List<NoteListArray> objects, boolean isPublisher){
        super(context,resource,objects);
        recourceId=resource;
        this.isPublisher = isPublisher;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        NoteListArray noteListArray = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(recourceId,parent,false);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);
        if(isPublisher){
            deleteBtn.setVisibility(View.VISIBLE);
        } else {
            deleteBtn.setVisibility(View.INVISIBLE);
        }
        ImageView imageView=view.findViewById(R.id.note_pic);
        TextView tv_noteName = view.findViewById(R.id.note_name);
        TextView tv_noteUserName = view.findViewById(R.id.note_userName);
        TextView identity_doctor = view.findViewById(R.id.identity_doctor);
        TextView identity_user = view.findViewById(R.id.identity_user);
        String url="http://172.20.10.10:8081/popmh/noteCover/"+noteListArray.getCoverId();
        Log.d("url",url);
        Glide.with(view.getContext()).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        tv_noteName.setText(noteListArray.getName());
        tv_noteUserName.setText(noteListArray.getUserName());
        if(noteListArray.getUserLevel() == 0){
            identity_doctor.setVisibility(View.VISIBLE);
            identity_user.setVisibility(View.INVISIBLE);
        } else {
            identity_doctor.setVisibility(View.INVISIBLE);
            identity_user.setVisibility(View.VISIBLE);
        }
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("提示");
                dialog.setMessage("确定删除吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = AppInfo.IP + "noteInfo/" + noteListArray.getId();
                        OkHttpClient okHttpClient=new OkHttpClient();
                        Request request=new Request.Builder().url(url).delete().build();
                        Call call=okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("Fail", "删除失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                ResponseBody body = response.body();
                                String json = body.string();
                                JSONObject root = JSONObject.parseObject(json);
                                if (root.getString("code").equals("0") ){
                                    Looper.prepare();
                                    Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}
