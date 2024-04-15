package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.MyPaperTestAdapter;
import com.example.mentalhealth.entity.MyPaperTestArray;
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

public class MyPaperTest extends AppCompatActivity {

    private ListView myPaper_lv;
    private List<MyPaperTestArray> paperList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_paper_test);
        myPaper_lv = findViewById(R.id.myPaperList);
        loadData();
    }

    private void loadData(){
        String url = "http://172.20.10.10:8081/popmh/paperResult/findByUserId/" + AppInfo.personNow.getId();
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
                myPaper_lv.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray paperArray= JSON.parseArray(json);
                        for(int i=0;i<paperArray.size();i++){
                            JSONObject paperObject = paperArray.getJSONObject(i);
                            MyPaperTestArray myPaperTestArray = new MyPaperTestArray();
                            myPaperTestArray.setId(paperObject.getString("id"));
                            myPaperTestArray.setPaperId(paperObject.getString("paperId"));
                            myPaperTestArray.setPaperTitle(paperObject.getString("paperName"));
                            myPaperTestArray.setDescription(paperObject.getString("description"));
                            paperList.add(myPaperTestArray);
                        }
                        MyPaperTestAdapter myPaperTestAdapter = new MyPaperTestAdapter(MyPaperTest.this,R.layout.my_paper_lv,paperList);
                        myPaper_lv.setAdapter(myPaperTestAdapter);
//                        myPaper_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                String paperResultId = paperList.get(position).get("paperResultId").toString();
//                                String paperId = paperList.get(position).get("paperId").toString();
//                                String paperTitle = paperList.get(position).get("paperTitle").toString();
//                                Intent intent = new Intent(MyPaperTest.this, paperDetail.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("paperResultId", paperResultId);
//                                bundle.putString("paperId", paperId);
//                                bundle.putString("paperTitle",paperTitle);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                            }
//                        });
                    }
                });
            }
        });
    }
}