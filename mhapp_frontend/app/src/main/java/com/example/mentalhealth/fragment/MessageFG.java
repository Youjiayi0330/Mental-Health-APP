package com.example.mentalhealth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.MessageListAdapter;
import com.example.mentalhealth.ChatMainActivity;
import com.example.mentalhealth.R;
import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.netty.message.DataRequest;
import com.example.mentalhealth.netty.pojo.ChatListVo;
import com.example.mentalhealth.netty.pojo.ChatRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MessageFG extends Fragment {
    public List<ChatListVo> data;
    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private Handler handler;
    private SwipeRefreshLayout fresh;
    private View view;

    public MessageFG(){
        initHandler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(data == null){
            data = new ArrayList<>();
        }
    }

    @Override
    //加载Fragment中的layout
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.message_fg,null);
        fillAllView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter = new MessageListAdapter(view.getContext(),data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        AppInfo.clientChannel.writeAndFlush(new DataRequest(AppInfo.personNow.getId()));
        setListener();
        return view;
    }

    private void setListener(){
        adapter.setMlistener(new MessageListAdapter.OnRecyclerItemListener() {
            @Override
            public void onRecyclerItemClick(ChatListVo d, int position) {
                System.out.println("我进入了与"+d.getPersonId()+"的聊天界面...");
                Intent intent = new Intent(view.getContext(), ChatMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("friendId",d.getPersonId());
                bundle.putString("friendName",d.getPersonName());
                intent.putExtras(bundle);
                d.setHasRead(1);
                adapter.notifyItemChanged(position);
                startActivity(intent);
            }
        });
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppInfo.clientChannel.writeAndFlush(new DataRequest(AppInfo.personNow.getId()));
                fresh.setRefreshing(false);
            }
        });
    }

    private void fillAllView(){
        fresh = view.findViewById(R.id.message_fragment_fresh);
        recyclerView = view.findViewById(R.id.message_fragment_recyclerView);
    }

    private void initHandler(){
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                //刷新所有数据
                if(msg.what == 0){
                    AllDataChanged(msg);
                }
                //别人给我发消息
                if(msg.what == 1){
                    SomeChanged(msg);
                }
                //我给别人发消息
                if(msg.what == 2){
                    ChatRecord record = (ChatRecord) msg.obj;
                    String friendId = record.getFriendId();
                    if(data == null){
                        data = new ArrayList<>();
                    }
                    int size = data.size(), i;
                    for(i=0; i<size; i++){
                        if(data.get(i).getPersonId().equals(friendId))
                            break;
                    }
                    //聊天对象已在消息列表中
                    if(i<size){
                        ChatListVo chatListVo = data.get(i);
                        //更新与friend聊天的相关信息
                        chatListVo.setMessage(record.getMessage());
                        chatListVo.setCreateTime(record.getCreateTime());
                        chatListVo.setHasRead(1);
                        data.set(i,chatListVo);
                        adapter.notifyItemChanged(i);
                    } else { //从前没有发送过消息的friend
                        String url="http://172.20.10.10:8081/popmh/person/findNameById/"+friendId;
                        OkHttpClient okHttpClient=new OkHttpClient();
                        Request request=new Request.Builder().url(url).get().build();
                        Call call=okHttpClient.newCall(request);
                        Log.d("call","使用call加入请求队列");
                        call.enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("Fail","call加入队列失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                ChatListVo chatListVo = null;
                                ResponseBody body = response.body();
                                String json = body.string();
                                JSONObject root = JSONObject.parseObject(json);
                                String friendName = root.getString("data");
                                chatListVo = new ChatListVo(friendId,friendName,record.getMessage(),record.getCreateTime(),1);
                                if (chatListVo != null){
                                    data.add(chatListVo);
                                    adapter.notifyItemInserted(data.size()-1);
                                }
                            }
                        });
                    }
                }
            }
        };
        AllHandler.messageHandler = handler;
    }

    //加载所有信息列表
    private void AllDataChanged(@NonNull Message message){
        List<ChatListVo> d = (List<ChatListVo>) message.obj;
        data.clear();
        data.addAll(d);
        adapter.notifyDataSetChanged();
    }

    //friend给我发送消息
    private void SomeChanged(@NonNull Message msg){
        ChatRecord record = (ChatRecord) msg.obj;
        String personId = record.getPersonId();
        int size = data.size(), i;
        for(i=0;i<size;i++){
            if(data.get(i).getPersonId().equals(personId)){
                break;
            }
        }
        //该friend在消息列表中
        if(i<size){
            ChatListVo chatListVo = data.get(i);
            chatListVo.setMessage(record.getMessage());
            chatListVo.setCreateTime(record.getCreateTime());
            chatListVo.setHasRead(record.getHasRead());
            data.set(i,chatListVo);
            adapter.notifyItemChanged(i);
        } else { //friend第一次发消息
            String url="http://172.20.10.10:8081/popmh/person/findNameById/"+personId;
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder().url(url).get().build();
            Call call=okHttpClient.newCall(request);
            Log.d("call","使用call加入请求队列");
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Fail","call加入队列失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ChatListVo chatListVo = null;
                    ResponseBody body = response.body();
                    String json = body.string();
                    JSONObject root = JSONObject.parseObject(json);
                    String friendName = root.getString("data");
                    chatListVo = new ChatListVo(personId,friendName,record.getMessage(),record.getCreateTime(),0);
                    if (chatListVo != null){
                        data.add(0,chatListVo);
                        adapter.notifyItemInserted(0);
                    }
                }
            });
        }
    }
}
