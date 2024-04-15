package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class passageDetail extends AppCompatActivity {
    private TextView tv_ptitle;
    private TextView tv_ptime;
    private WebView wv_pcontent;
    private Button collectFalseBtn, collectTrueBtn;
    private boolean isCollect = false;
    private String passageId;
    private static final String BASE_PATH = "http://172.20.10.10:8081/popmh/collect";
    private String url;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 0){
                    //表示取消收藏，false按钮显示
                    collectFalseBtn.setVisibility(View.VISIBLE);
                    collectTrueBtn.setVisibility(View.INVISIBLE);
                    Log.d("collect","取消收藏");
                } else if(msg.what == 1){
                    //表示添加收藏，true按钮显示
                    collectFalseBtn.setVisibility(View.INVISIBLE);
                    collectTrueBtn.setVisibility(View.VISIBLE);
                    Log.d("collect","添加收藏");
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage_detail);
        initView();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String key=bundle.getString("key");
        passageId = bundle.getString("passageId");
        url = BASE_PATH + "/" + passageId +"?userId=" + AppInfo.personNow.getId();
        loadView(tv_ptitle,tv_ptime,wv_pcontent,key);
        setListener();
    }

    public void setListener(){
        collectTrueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).delete().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fail","取消收藏失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        Toast.makeText(passageDetail.this,"取消收藏",Toast.LENGTH_SHORT).show();
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                        Looper.loop();
                    }
                });
            }
        });
        collectFalseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("passageId",passageId)
                        .addFormDataPart("userId",AppInfo.personNow.getId())
                        .build();
                Request request = new Request.Builder().url(BASE_PATH).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fail","添加收藏失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        Toast.makeText(passageDetail.this,"已收藏",Toast.LENGTH_SHORT).show();
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        Looper.loop();
                    }
                });
            }
        });
    }

    public void loadView(TextView tv_ptitle,TextView tv_ptime,WebView wv_pcontent,String key){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(key).get().build();
        Call call = okHttpClient.newCall(request);
        String url_collect = "http://172.20.10.10:8081/popmh/collect/findIsCollect/"+passageId+"?userId="+AppInfo.personNow.getId();
        Request request1 = new Request.Builder().url(url_collect).get().build();
        Call call1 = okHttpClient.newCall(request1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求回调失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body=response.body();
                String json= body.string();
                JSONObject root = JSONObject.parseObject(json);
                JSONObject detailPassage = root.getJSONObject("data");
                tv_ptitle.post(new Runnable() {
                    @Override
                    public void run() {
                        String ptitle=detailPassage.getString("ptitle");
                        tv_ptitle.setText(ptitle);
                        Log.d("PassageDetail",tv_ptitle.getText().toString());
                    }
                });
                tv_ptime.post(new Runnable() {
                    @Override
                    public void run() {
                        String ptime=detailPassage.getString("ptime");
                        tv_ptime.setText(ptime);
                        Log.d("PassageDetail",tv_ptime.getText().toString());
                    }
                });
                wv_pcontent.post(new Runnable() {
                    @Override
                    public void run() {
                        String pcontnet=detailPassage.getString("pcontent");
                        wv_pcontent.loadData(pcontnet,"text/html","utf-8");
                        Log.d("PassageDetail",wv_pcontent.toString());
                    }
                });

            }
        });
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求收藏信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String collectJudge = body.string();
                if(collectJudge.equals("true")){
                    isCollect = true;
                    collectFalseBtn.setVisibility(View.INVISIBLE);
                    collectTrueBtn.setVisibility(View.VISIBLE);
                } else {
                    isCollect = false;
                    collectFalseBtn.setVisibility(View.VISIBLE);
                    collectTrueBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void initView(){
        tv_ptitle=findViewById(R.id.ptitle);
        tv_ptime=findViewById(R.id.ptime);
        wv_pcontent=findViewById(R.id.pcontent);
        collectFalseBtn = findViewById(R.id.collect_false);
        collectTrueBtn = findViewById(R.id.collect_true);
    }
}