package com.example.mentalhealth.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.DoctorListAdapter;
import com.example.mentalhealth.Adapter.MyGridViewAdapter;
import com.example.mentalhealth.AddNote;
import com.example.mentalhealth.R;
import com.example.mentalhealth.doctorDetail;
import com.example.mentalhealth.entity.DoctorListArray;
import com.example.mentalhealth.entity.NoteListArray;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.noteDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CommunityFG extends Fragment {

    private GridView noteGridView;
    private List<NoteListArray> notelist;
    private Button addNoteBtn;
    private View view;

    private static final int REQUEST_CODE_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    //加载Fragment中的layout
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.community_fg,null);
        noteGridView = view.findViewById(R.id.gridview);
        addNoteBtn = view.findViewById(R.id.addNoteBtn);
        notelist = new ArrayList<>();
        fillData(noteGridView);
        setListener();
        return view;
    }

    public void setListener(){
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.personNow == null){
                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println(AppInfo.personNow);
                    Intent intent = new Intent(view.getContext(),AddNote.class);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    public void fillData(View view){
        String url = "http://172.20.10.10:8081/popmh/noteInfo/all";
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
                        boolean isPublisher = false;
                        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(view.getContext(),R.layout.layout_gv,notelist,isPublisher);
                        noteGridView.setAdapter(myGridViewAdapter);

                        noteGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                String url = "http://172.20.10.10:8081/popmh/noteInfo/";
//                                StringBuilder stringBuilder = new StringBuilder(url);
//                                stringBuilder.append(notelist.get(position).getId());
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
