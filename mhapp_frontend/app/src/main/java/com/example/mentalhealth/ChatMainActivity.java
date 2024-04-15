package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentalhealth.Adapter.ChatMessageAdapter;
import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.netty.message.ChatRequestMessage;
import com.example.mentalhealth.netty.message.MessageHasReadRequest;
import com.example.mentalhealth.netty.message.MessageRecordRequest;
import com.example.mentalhealth.netty.pojo.ChatRecord;
import com.example.mentalhealth.netty.pojo.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageEdit;
    private ImageView send;
    private TextView name;
    private Handler handler;
    private List<ChatRecord> data;
    private Person friend;
    private ChatMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 0){
                    data.addAll((List<ChatRecord>) msg.obj );
                    System.out.println("更新消息" + msg.obj);
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 1) {//更新局部的消息
                    ChatRecord record = (ChatRecord) msg.obj;
                    System.out.println("收到" + record.getPersonId() + "的一条消息");
                    //如果就是我正在和好友聊天，则实时更新数据
                    if (friend.getId().equals(record.getPersonId())) {
                        data.add(record);
                        //将此消息设置为已读
                        AppInfo.clientChannel.writeAndFlush(new MessageHasReadRequest(AppInfo.personNow.getId(),friend.getId()));
                        System.out.println("实时更新聊天记录...");
                        adapter.notifyItemInserted(data.size() - 1);
                        recyclerView.scrollToPosition(data.size() - 1);
                    }
                }
            }
        };
        AllHandler.chatRecordHandler = handler;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        findAllView();
        initData();
        setListener();
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(false);
        adapter = new ChatMessageAdapter(this,friend,data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layout);
        AppInfo.clientChannel.writeAndFlush(new MessageRecordRequest(AppInfo.personNow.getId(),friend.getId()));
    }

    @Override//点击返回键同样要返回一个数据给数据库
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AllHandler.chatRecordHandler = null;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setListener(){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageEdit.getText().toString();
                if(msg.equals("")) return;
                ChatRequestMessage chatRequestMessage = new ChatRequestMessage(AppInfo.personNow.getId(),friend.getId(),msg);
                AppInfo.clientChannel.writeAndFlush(chatRequestMessage);
                Date createTime = new Date();
                ChatRecord record = new ChatRecord(AppInfo.personNow.getId(),friend.getId(),0,createTime,msg);
                data.add(record);
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                //更新消息列表
                Message message = new Message();
                message.what = 2;
                message.obj = record;
                AllHandler.messageHandler.sendMessage(message);
                messageEdit.setText("");
            }
        });
    }

    private void initData(){
        data = new ArrayList<>();
        friend = new Person();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null && bundle.containsKey("id")){
            String id = bundle.getString("id");
            String nickname = bundle.getString("nickname");
            friend.setId(id);
            friend.setNickname(nickname);
            name.setText(nickname);
        } else if(bundle!=null && bundle.containsKey("friendId")){
            friend.setId(bundle.getString("friendId"));
            friend.setNickname(bundle.getString("friendName"));
            name.setText(bundle.getString("friendName"));
        }
    }

    private void findAllView(){
        recyclerView = findViewById(R.id.chat_message_recycleview);
        messageEdit = findViewById(R.id.chat_message_edit);
        send = findViewById(R.id.chat_message_send);
        name = findViewById(R.id.chat_message_name);
    }
}