package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PaperList extends AppCompatActivity {
    private ListView paperListView;
    private List<Map<String,Object>> paperList;
    private Button backHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_list);
        paperList = new ArrayList<>();
        paperListView = findViewById(R.id.paper_list);
        backHomeBtn = findViewById(R.id.back_home);
        fillData(paperListView);
        setListener();
    }

    private void setListener(){
        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaperList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillData(View view){
        String url = "http://172.20.10.10:8081/popmh/paper/all";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求paperList失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray paperArray= JSON.parseArray(json);
                        for(int i=0;i<paperArray.size();i++){
                            JSONObject paperObject = paperArray.getJSONObject(i);
                            Map<String,Object> paperMap = new HashMap<>();
                            paperMap.put("paperId", paperObject.getString("id"));
                            paperMap.put("paperTitle", paperObject.getString("paperTitle"));
                            paperList.add(paperMap);
                        }
                        SimpleAdapter simpleAdapter = new SimpleAdapter(
                                view.getContext(),
                                paperList,
                                R.layout.paper_lv,
                                new String[]{"paperTitle"},
                                new int[]{R.id.tv_paperTitle}
                        );
                        paperListView.setAdapter(simpleAdapter);

                        paperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(AppInfo.personNow == null){
                                    Toast.makeText(PaperList.this,"请先登录",Toast.LENGTH_SHORT).show();
                                } else {
                                    String paperId = paperList.get(position).get("paperId").toString();
                                    String paperTitle = paperList.get(position).get("paperTitle").toString();
                                    Intent intent = new Intent(PaperList.this, paperDetail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("paperId", paperId);
                                    bundle.putString("paperTitle",paperTitle);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}