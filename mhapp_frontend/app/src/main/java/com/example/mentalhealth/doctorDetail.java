package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class doctorDetail extends AppCompatActivity {

    private TextView tv_nickname,tv_sign,tv_intro;
    private Button order,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String key = bundle.getString("key");
        String id = bundle.getString("id");
        String nickname = bundle.getString("nickname");
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.personNow == null){
                    Toast.makeText(doctorDetail.this,"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(doctorDetail.this, ChatMainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id);
                    bundle.putString("nickname",nickname);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        loadView(tv_nickname,tv_sign,tv_intro,key);
    }

    public void loadView(TextView tv_nickname, TextView tv_sign, TextView tv_intro, String key){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(key).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求回调失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                JSONObject root = JSONObject.parseObject(json);
                JSONObject detailDoctor = root.getJSONObject("data");
//                chat.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(doctorDetail.this,ChatMainActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id",id);
//                        bundle.putString("nickname",nickname);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
                tv_nickname.post(new Runnable() {
                    @Override
                    public void run() {
                        String nickname = detailDoctor.getString("nickname");
                        tv_nickname.setText(nickname);
                    }
                });
                tv_sign.post(new Runnable() {
                    @Override
                    public void run() {
                        String sign = detailDoctor.getString("sign");
                        tv_sign.setText(sign);
                    }
                });
                tv_intro.post(new Runnable() {
                    @Override
                    public void run() {
                        String intro = detailDoctor.getString("intro");
                        tv_intro.setText(intro);
                    }
                });
            }
        });
    }

    public void initView(){
        tv_nickname = findViewById(R.id.doctor_nickname);
        tv_sign = findViewById(R.id.doctor_sign);
        tv_intro = findViewById(R.id.doctor_intro);
        order = findViewById(R.id.goOrder);
        chat = findViewById(R.id.goChat);
    }
}