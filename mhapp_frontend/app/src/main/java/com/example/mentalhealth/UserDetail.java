package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UserDetail extends AppCompatActivity {
    private EditText nicknameEdt, pwdEdt, againPwdEdt;
    private Button updatePwdBtn, submitBtn;
    private TextView tv_warn, tv_againPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initView();
        nicknameEdt.setText(AppInfo.personNow.getNickname());
        pwdEdt.setText(AppInfo.personNow.getPwd());
        againPwdEdt.setText(AppInfo.personNow.getPwd());
        againPwdEdt.setVisibility(View.INVISIBLE);
        tv_warn.setVisibility(View.INVISIBLE);
        tv_againPwd.setVisibility(View.INVISIBLE);
        setListener();
    }

    public void setListener(){
        updatePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                againPwdEdt.setVisibility(View.VISIBLE);
                tv_againPwd.setVisibility(View.VISIBLE);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwdEdt.getText().toString().equals(againPwdEdt.getText().toString())){
                    String url = AppInfo.IP +"person/update";
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id",AppInfo.personNow.getId())
                            .addFormDataPart("nickname",nicknameEdt.getText().toString())
                            .addFormDataPart("pwd",pwdEdt.getText().toString())
                            .build();
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(url).put(requestBody).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            ResponseBody body = response.body();
                            String json = body.string();
                            JSONObject root = JSONObject.parseObject(json);
                            if (root.getString("code").equals("0") ){
                                Looper.prepare();
                                Toast.makeText(UserDetail.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    });
                } else {
                    tv_warn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initView(){
        nicknameEdt = findViewById(R.id.edt_nickname);
        pwdEdt = findViewById(R.id.edt_pwd);
        againPwdEdt = findViewById(R.id.edt_again_pwd);
        updatePwdBtn = findViewById(R.id.btn_update_pwd);
        submitBtn = findViewById(R.id.btn_update_user);
        tv_warn = findViewById(R.id.tv_warn);
        tv_againPwd = findViewById(R.id.tv_againPwd);
    }
}