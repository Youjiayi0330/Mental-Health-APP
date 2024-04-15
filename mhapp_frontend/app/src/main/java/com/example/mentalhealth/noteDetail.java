package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mentalhealth.Adapter.ImageListAdapter;
import com.example.mentalhealth.Adapter.NoteCommentAdapter;
import com.example.mentalhealth.Adapter.NoteImagePageAdapter;
import com.example.mentalhealth.entity.NoteComment;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class noteDetail extends AppCompatActivity {
    private Button commentBtn, praiseBtn, goCommentBtn, commentSendBtn;
    private TextView commentCount, praiseCount;
    private EditText commentEdt;
    private View comment_edit_pop;
    private LinearLayout point_detail;

    private TextView noteName, noteUserName, noteTime, noteContent;
    private ListView commentlistView;
    private RecyclerView commentrv;
    private ViewPager viewPager;
    private ArrayList<String> imageIdList = new ArrayList<>();
    private ArrayList<View> imageViews = new ArrayList<>();
    //private List<Map<String,Object>> noteCommentList = new ArrayList<>();
    private List<NoteComment> noteCommentList = new ArrayList<>();
    private NoteImagePageAdapter noteImagePageAdapter;
    private NoteCommentAdapter noteCommentAdapter;

    private String noteId;
    private Handler handler;
    //private SimpleAdapter simpleAdapter;
    private PopupWindow popupWindow;

    public static final String BASE_PATH = "http://172.20.10.10:8081/popmh/noteImages/";

    private int dpToPx(int dp) {
        //获取手机屏幕像素密度
        float phoneDensity = getResources().getDisplayMetrics().density;
        //加0.5f是为了四舍五入 避免丢失精度
        return (int) (dp * phoneDensity + 0.5f);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 0){
                    System.out.println("更新数据");
                    //simpleAdapter.notifyDataSetChanged();
                    //loadComment(noteId);
                    NoteComment noteComment = (NoteComment) msg.obj;
                    noteCommentList.add(noteComment);
                    noteCommentAdapter.refresh(noteCommentList);
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        noteId=bundle.getString("key");
        initView();
        loadView(noteId);
        loadComment(noteId);
        setListener();
    }

    public void initIndicatorView() {
        point_detail=findViewById(R.id.point_detail);

        for (int i = 0; i < imageViews.size(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpToPx(6), dpToPx(6));
            lp.leftMargin = dpToPx(10);
            lp.bottomMargin = dpToPx(6);

            View ivIndicator = new View(this);
            //[R.drawable.indicator_select]为指示器的背景资源 相关样式可替换
            ivIndicator.setBackgroundResource(R.drawable.indicator_select);
            ivIndicator.setLayoutParams(lp);
            ivIndicator.setEnabled(false);
            //将一个个指示器(ImageView)添加到父布局中
            point_detail.addView(ivIndicator);
        }
    }

    public void updateIndicatorSelectState(int position) {
        //循环获取指示器父布局中所有的子View
        for (int i = 0; i < point_detail.getChildCount(); i++) {
            //给每个子view设置选中状态
            //当i == position为True的时候触发选中状态反之则设置成未选中
            point_detail.getChildAt(i).setEnabled(i == position);
        }
    }

    public void initPageChangeListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int position1) {

            }

            @Override
            public void onPageSelected(int position) {
                //更新指示器选中状态
                updateIndicatorSelectState(position);
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    private void setListener(){
        goCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppInfo.personNow == null){
                    Toast.makeText(noteDetail.this,"请先登录",Toast.LENGTH_SHORT).show();
                } else {
                    popupWindow = new PopupWindow();
                    popupWindow.setContentView(comment_edit_pop);
                    popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
                    popupWindow.setHeight(250);
                    popupWindow.setFocusable(true);
                    popupWindow.setTouchable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(view.getRootView(), Gravity.BOTTOM, 0, 0);
                }
            }
        });
        commentSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://172.20.10.10:8081/popmh/noteComment";
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("noteId",noteId)
                        .addFormDataPart("userId", AppInfo.personNow.getId())
                        .addFormDataPart("content",commentEdt.getText().toString())
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("File","上传评论请求队列失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        Toast.makeText(noteDetail.this,"已评论",Toast.LENGTH_SHORT).show();
                        ResponseBody body = response.body();
                        String json = body.string();
                        JSONObject root = JSONObject.parseObject(json);
                        JSONObject noteCommentObject = root.getJSONObject("data");
                        NoteComment noteComment = new NoteComment(noteCommentObject.getString("id"),noteCommentObject.getString("time"),
                                noteCommentObject.getString("content"),AppInfo.personNow.getNickname());
                        Message message = new Message();
                        message.what = 0;
                        message.obj = noteComment;
                        handler.sendMessage(message);
                        System.out.println("评论更新");
                        //simpleAdapter.notifyDataSetChanged();
//                        popupWindow.dismiss();
                        Looper.loop();
//                        Intent intent = new Intent(noteDetail.this,noteDetail.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("key",noteId);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void loadView(String key){
        String url = "http://172.20.10.10:8081/popmh/noteInfo/"+key;
        String url_images = "http://172.20.10.10:8081/popmh/noteImage/" + key;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Request request_images = new Request.Builder().url(url_images).get().build();
        Call call = okHttpClient.newCall(request);
        Call call_images = okHttpClient.newCall(request_images);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求笔记内容回调失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                JSONObject root = JSONObject.parseObject(json);
                JSONObject detailNote = root.getJSONObject("data");
                noteName.post(new Runnable() {
                    @Override
                    public void run() {
                        String name = detailNote.getString("name");
                        noteName.setText(name);
                    }
                });
                noteUserName.post(new Runnable() {
                    @Override
                    public void run() {
                        String userName = detailNote.getString("userName");
                        noteUserName.setText(userName);
                    }
                });
                noteTime.post(new Runnable() {
                    @Override
                    public void run() {
                        String time = detailNote.getString("time");
                        noteTime.setText(time);
                    }
                });
                noteContent.post(new Runnable() {
                    @Override
                    public void run() {
                        String content = detailNote.getString("content");
                        noteContent.setText(content);
                    }
                });
            }
        });
        call_images.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求笔记图片回调失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                System.out.println(json);
                JSONArray imageArray = JSON.parseArray(json);
                for(int i=0;i<imageArray.size();i++) {
                    JSONObject imageObject = imageArray.getJSONObject(i);
                    String imageId = imageObject.getString("id");
                    imageIdList.add(imageId);
                }
                for(int i=0;i<imageIdList.size();i++){
                    View view = getLayoutInflater().inflate(R.layout.banner_note_image,null,false);
                    String url = BASE_PATH+imageIdList.get(i);
                    System.out.println("图片路径"+url);
                    ImageView imageView = view.findViewById(R.id.iv_note_image);
                    Glide.with(view.getContext()).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
                    imageViews.add(view);
                }
                noteImagePageAdapter = new NoteImagePageAdapter(imageViews);
                initIndicatorView();
                initPageChangeListener();
                viewPager.setCurrentItem(0);
                viewPager.setAdapter(noteImagePageAdapter);
            }
        });

    }

    public void loadComment(String key){
        String url_comment = "http://172.20.10.10:8081/popmh/noteComment/findByNoteId/" + key;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request_comments = new Request.Builder().url(url_comment).get().build();
        Call call_comments = okHttpClient.newCall(request_comments);
        call_comments.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求笔记评论回调失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                JSONArray commentArray = JSON.parseArray(json);
                for (int i = 0; i < commentArray.size(); i++) {
                    JSONObject commentObject = commentArray.getJSONObject(i);
                    String id = commentObject.getString("id");
                    String time = commentObject.getString("time");
                    String userName = commentObject.getString("userName");
                    String content = commentObject.getString("content");
                    NoteComment noteComment = new NoteComment(id,time,content,userName);
                    noteCommentList.add(noteComment);
//                    Map<String,Object> commentMap = new HashMap<>();
//                    commentMap.put("time",commentObject.getString("time"));
//                    commentMap.put("content",commentObject.getString("content"));
//                    commentMap.put("userName",commentObject.getString("userName"));
//                    noteCommentList.add(commentMap);
//                }
//                simpleAdapter = new SimpleAdapter(
//                        noteDetail.this,
//                        noteCommentList,
//                        R.layout.commnet_lv,
//                        new String[]{"time","content","userName"},
//                        new int[]{R.id.comment_time,R.id.comment_content,R.id.comment_userName}
//                );
//                commentlistView.setAdapter(simpleAdapter);
//                setListViewHeightBasedOnChildren(commentlistView,simpleAdapter);
                }
                noteCommentAdapter = new NoteCommentAdapter(noteDetail.this,noteCommentList);
                commentrv.setAdapter(noteCommentAdapter);
                commentrv.setLayoutManager(new LinearLayoutManager(noteDetail.this));
                //setListViewHeightBasedOnChildren(commentrv,noteCommentAdapter);
            }
        });
    }

//    public void setListViewHeightBasedOnChildren(RecyclerView listView, NoteCommentAdapter simpleAdapter) {
//        // 获取ListView对应的Adapter
//        if (simpleAdapter == null) {
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i = 0, len = simpleAdapter.getItemCount(); i < len; i++) {
//            // listAdapter.getCount()返回数据项的数目
//            View listItem = simpleAdapter.onCreateViewHolder()
//            //View listItem = simpleAdapter.getView(i, null, listView);
//            // 计算子项View 的宽高
//            listItem.measure(0, 0);
//            // 统计所有子项的总高度
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight;
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
//    }

    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(noteDetail.this);
        comment_edit_pop = (ScrollView) inflater.inflate(R.layout.comment_edit_pop,null);
        commentSendBtn = comment_edit_pop.findViewById(R.id.comment_send_btn);
        commentEdt = comment_edit_pop.findViewById(R.id.comment_content_edt);
        commentBtn = findViewById(R.id.comment_visit_btn);
        praiseBtn = findViewById(R.id.praise_btn);
        goCommentBtn = findViewById(R.id.go_comment_btn);
        commentCount = findViewById(R.id.comment_count);
        praiseCount = findViewById(R.id.praise_count);

        noteName = findViewById(R.id.note_detail_name);
        noteUserName = findViewById(R.id.note_detail_userName);
        noteTime = findViewById(R.id.note_detail_time);
        noteContent = findViewById(R.id.note_detail_content);
        viewPager = findViewById(R.id.note_images_pager);
        //commentlistView = findViewById(R.id.commentlist);
        commentrv = findViewById(R.id.note_comment_recycleview);
    }
}