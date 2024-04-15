package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.ImageListAdapter;
import com.example.mentalhealth.entity.ImageListArray;
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

public class MyCollect extends AppCompatActivity {

    private ListView collectListView;
    //private List<ImageListArray> collectList = new ArrayList<>();
    private SwipeRefreshLayout fresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        collectListView = findViewById(R.id.collect_list);
        fresh = findViewById(R.id.collect_fresh);
        fillData();
        setListener();
    }

    public void setListener(){
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillData();
                fresh.setRefreshing(false);
            }
        });
    }

    public void fillData(){
        List<ImageListArray> collectList = new ArrayList<>();
        String url="http://172.20.10.10:8081/popmh/collect/findByUserId/"+ AppInfo.personNow.getId();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求用户收藏失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                collectListView.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray passageArray= JSON.parseArray(json);
                        int size=passageArray.size();
                        for(int i=0;i<size;i++){
                            JSONObject passageObject=passageArray.getJSONObject(i);
                            //Map<String,Object> passage=new HashMap<>();
                            Long pid=passageObject.getLong("pid");
                            String ptitle=passageObject.getString("ptitle");
                            String ptime=passageObject.getString("ptime");
                            String fileName=passageObject.getString("fileName");
                            Integer collect_conunt = passageObject.getInteger("collect_count");
                            Log.d("fileName",fileName);
                            ImageListArray imageListArray=new ImageListArray(pid,ptitle,ptime,fileName,collect_conunt);
                            collectList.add(imageListArray);
                        }
                        ImageListAdapter imageListAdapter=new ImageListAdapter(collectListView.getContext(),R.layout.passage_lv,collectList);
                        collectListView.setAdapter(imageListAdapter);
                        collectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if(AppInfo.personNow != null){
                                    String url="http://172.20.10.10:8081/popmh/passage/";
                                    StringBuilder stringBuilder = new StringBuilder(url);
                                    stringBuilder.append(collectList.get(i).getPid().toString());
                                    Intent intent = new Intent(view.getContext(), passageDetail.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("key", stringBuilder.toString());
                                    bundle.putString("passageId", collectList.get(i).getPid().toString());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}