package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.netty.Client;
import com.example.mentalhealth.netty.message.RegisterRequestMessage;
import com.example.mentalhealth.netty.message.RegisterResponseMessage;
import com.example.mentalhealth.netty.pojo.Person;

public class RegisterActivity extends AppCompatActivity {
    private EditText idEdt, nickNameEdt, pwdEdt, pwdAgainEdit;
    private RadioGroup identityGroup;
    private Button registerBtn;
    private String id, nickName, pwd, pwdAgain;
    private Integer identity = -1, status = -1;
    private Handler handler;
    private Client client = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler =  new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 0){
                    RegisterResponseMessage message = (RegisterResponseMessage) msg.obj;
                    if(message.isSuccess()){
                        if(identity == 1) {
                            Toast.makeText(RegisterActivity.this,message.getReason(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this,"注册成功，继续认证",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,doctor_certify.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id",id);
                            intent.putExtras(bundle);
                            RegisterActivity.this.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this,message.getReason(),Toast.LENGTH_SHORT).show();
                    }
                    AppInfo.clientChannel.close();
                }
            }
        };
        AllHandler.registerHandler = handler;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fillAllView();
        setListener();
    }

    private void setListener(){
        identityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                switch (i){
                    case R.id.doctor:
                        identity = 0;
                        status = 0;
                        Log.d("client","身份为医生");
                        break;
                    case R.id.user:
                        identity = 1;
                        status = 1;
                        Log.d("client","身份为用户");
                        break;
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterValue();
                //判断输入框的内容
                if (TextUtils.isEmpty(id)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(nickName)){
                    Toast.makeText(RegisterActivity.this,"请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this,"请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(pwdAgain)){
                    Log.d("client","密码不一致");
                    Toast.makeText(RegisterActivity.this,"请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if(identity == -1){
                    Toast.makeText(RegisterActivity.this,"未选择身份", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!pwdAgain.equals(pwd)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                }
                //开始注册
                client.start();
                //Log.d("client",AppInfo.clientChannel.localAddress().toString());
                //循环等待
                int time=0;
                while(AppInfo.clientChannel == null){
                    try {
                        Thread.sleep(200);
                        time+=200;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (time>=10000) {
                        Toast.makeText(RegisterActivity.this, "请求网络超时", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Person person = new Person();
                person.setId(id);
                person.setNickname(nickName);
                person.setPwd(pwd);
                person.setIdentity(identity);
                person.setStatus(status);
                AppInfo.clientChannel.writeAndFlush(new RegisterRequestMessage(person));
                AppInfo.clientChannel.writeAndFlush(id);
                //AppInfo.clientChannel.writeAndFlush(person);
                //AppInfo.clientChannel.writeAndFlush(nickName);
            }
        });
    }

    private void getRegisterValue(){
        id = idEdt.getText().toString().trim();
        nickName = nickNameEdt.getText().toString().trim();
        pwd = pwdEdt.getText().toString().trim();
        pwdAgain = pwdAgainEdit.getText().toString().trim();
    }

    private void fillAllView(){
        idEdt = findViewById(R.id.register_id);
        nickNameEdt = findViewById(R.id.register_nickName);
        pwdEdt = findViewById(R.id.register_password);
        pwdAgainEdit = findViewById(R.id.register_again);
        identityGroup = findViewById(R.id.identity);
        registerBtn = findViewById(R.id.register_btn);
    }
}