package com.example.mentalhealth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mentalhealth.LoginActivity;
import com.example.mentalhealth.MainActivity;
import com.example.mentalhealth.MyCollect;
import com.example.mentalhealth.MyNote;
import com.example.mentalhealth.MyPaperTest;
import com.example.mentalhealth.R;
import com.example.mentalhealth.UserDetail;
import com.example.mentalhealth.netty.AppInfo;

public class UserFG extends Fragment {

    private Button loginBtn,loginNickname,logoutBtn;
    private Button testBtn, collectBtn, userBtn, noteBtn;

    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    //加载Fragment中的layout
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.user_fg,null);
        initView();
        if(AppInfo.personNow != null){
            loginBtn.setVisibility(View.INVISIBLE);
            loginNickname.setVisibility(View.VISIBLE);
            loginNickname.setText(AppInfo.personNow.getNickname());
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            loginBtn.setVisibility(View.VISIBLE);
            loginNickname.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfo.clientChannel.close();
                AppInfo.personNow = null;
                Toast.makeText(view.getContext(), "退出成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.personNow == null){
                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(view.getContext(), MyPaperTest.class);
                    startActivity(intent);
                }
            }
        });
        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.personNow == null){
                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(view.getContext(), MyCollect.class);
                    startActivity(intent);
                }
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.personNow == null){
                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(view.getContext(), UserDetail.class);
                    startActivity(intent);
                }
            }
        });
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.personNow == null){
                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(view.getContext(), MyNote.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public void initView(){
        loginBtn = view.findViewById(R.id.loginAndRegister);
        loginNickname = view.findViewById(R.id.login_nickname);
        logoutBtn = view.findViewById(R.id.logout);
        testBtn = view.findViewById(R.id.test);
        collectBtn = view.findViewById(R.id.collect);
        userBtn = view.findViewById(R.id.userInfo);
        noteBtn = view.findViewById(R.id.share);
    }
}
