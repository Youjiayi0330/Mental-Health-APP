package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResetPwd extends AppCompatActivity {

    private EditText emailEdt, codeEdt;
    private Button getCodeBtn, submitBtn;
    private String email, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initView();
        setListener();
    }

    public void setListener(){
        getCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEdt.getText().toString();
                String url = "http://172.20.10.10:8081/popmh/reset/getCode/"+ email;
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fail","获取验证码失败");
                        Looper.prepare();
                        Toast.makeText(ResetPwd.this,"获取验证码失败",Toast.LENGTH_SHORT);
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEdt.getText().toString();
                code = codeEdt.getText().toString();
                String url = AppInfo.IP + "reset/verifyCode";
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email",email)
                        .addFormDataPart("code",code)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fail","重置密码失败");
                        Looper.prepare();
                        Toast.makeText(ResetPwd.this,"重置密码失败",Toast.LENGTH_SHORT);
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        String json = body.string();
                        JSONObject root = JSONObject.parseObject(json);
                        if(root.getString("code").equals("0") ) {
                            Looper.prepare();
                            Toast.makeText(ResetPwd.this, "密码初始化为123456", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResetPwd.this, LoginActivity.class);
                            startActivity(intent);
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(ResetPwd.this, root.getString("data"), Toast.LENGTH_SHORT).show();
                            Looper.prepare();
                        }
                    }
                });
            }
        });
    }

    public void initView(){
        emailEdt = findViewById(R.id.edit_email);
        codeEdt = findViewById(R.id.edit_code);
        getCodeBtn = findViewById(R.id.btn_set_code);
        submitBtn = findViewById(R.id.submit_reset);
    }
}