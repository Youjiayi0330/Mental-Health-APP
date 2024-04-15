package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.netty.Client;
import com.example.mentalhealth.netty.message.LoginRequestMessage;

public class LoginActivity extends AppCompatActivity {

    public Client client;
    private EditText loginId, loginPwd;
    private Button registerBtn,loginBtn,loginAndRegister,loginNicknameBtn, resetPwdBtn;
    private Handler handler;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                int what = msg.what;
                switch (what) {
                    case 0:
                        SharedPreferences loginStatus = getSharedPreferences("loginStatus",MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginStatus.edit();
                        editor.putString("id",AppInfo.personNow.getId());
                        editor.putString("pwd",AppInfo.personNow.getPwd());
                        editor.apply();
                        Toast.makeText(LoginActivity.this,"欢迎你"+AppInfo.personNow.getNickname()+msg.obj,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        //LoginActivity.this.finish();
                        break;
                    case 1:
                        Toast.makeText(LoginActivity.this,"登录失败"+msg.obj,Toast.LENGTH_SHORT).show();
                        AppInfo.clientChannel.close();
                        break;
                }
            }
        };
        AllHandler.mainHandler = handler;
        setContentView(R.layout.activity_login);
        loginId = findViewById(R.id.login_id);
        loginPwd = findViewById(R.id.login_pwd);
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);
        resetPwdBtn = findViewById(R.id.resetPwd);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
        SharedPreferences loginStatus = getSharedPreferences("loginStatus",MODE_PRIVATE);
        String myUserId = loginStatus.getString("id",null);
        String myPassword = loginStatus.getString("pwd",null);
        if(myUserId != null && myPassword != null){
            loginId.setText(myUserId);
            loginPwd.setText(myPassword);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginId.getText().toString() == null || loginPwd.getText().toString() == null){
                    Toast.makeText(LoginActivity.this,"邮箱和密码不能为空",Toast.LENGTH_SHORT);
                    return ;
                }
                client = new Client();
                client.start();
                try{
                    int time = 0;
                    //循环等待
                    while (AppInfo.clientChannel == null) {
                        Thread.sleep(200);
                        time += 200;
                        if (time >= 10000)
                            throw new RuntimeException("服务器连接超时");
                    }
                    LoginRequestMessage loginRequestMessage = new LoginRequestMessage();
                    loginRequestMessage.setLoginId(loginId.getText().toString());
                    loginRequestMessage.setLoginPwd(loginPwd.getText().toString());
                    AppInfo.clientChannel.writeAndFlush(loginRequestMessage);
                }catch (Exception e){
                    Log.d("Login","登录未成功");
                    e.printStackTrace();
                }
            }
        });
        resetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPwd.class);
                startActivity(intent);
            }
        });
    }
}