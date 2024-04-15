package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.MyGridViewAdapter;
import com.example.mentalhealth.entity.NoteListArray;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyNote extends AppCompatActivity {
    private GridView noteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        noteGridView = findViewById(R.id.my_gridview);
        fillData(noteGridView);
    }

    public void fillData(View view){
        List<NoteListArray> notelist =new ArrayList<>();
        String url = AppInfo.IP + "noteInfo/findByUserId/" + AppInfo.personNow.getId();
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","call加入队列失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray noteArray = JSON.parseArray(json);
                        int size = noteArray.size();
                        for(int i=0; i<size; i++){
                            JSONObject noteObject = noteArray.getJSONObject(i);
                            String id = noteObject.getString("id");
                            String name = noteObject.getString("name");
                            String userName = noteObject.getString("userName");
                            Integer identity = noteObject.getInteger("identity");
                            String time = noteObject.getString("time");
                            String coverId = noteObject.getString("coverId");
                            NoteListArray noteListArray = new NoteListArray(id,name,userName,identity,time,coverId);
                            notelist.add(noteListArray);
                        }
                        boolean isPublisher = true;
                        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(view.getContext(),R.layout.layout_gv,notelist,isPublisher);
                        noteGridView.setAdapter(myGridViewAdapter);

                        noteGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                System.out.println("获取的id"+notelist.get(position).getId());
                                String noteId = notelist.get(position).getId();
                                Intent intent = new Intent(view.getContext(), noteDetail.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("key",noteId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }
}