package com.example.mentalhealth.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.ImageListAdapter;
import com.example.mentalhealth.Adapter.MyPagerAdapter;
import com.example.mentalhealth.PaperList;
import com.example.mentalhealth.R;
import com.example.mentalhealth.SearchPasage;
import com.example.mentalhealth.entity.ImageListArray;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.passageDetail;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainFG extends Fragment {
    private SearchView searchView;
    private View view;
    private ViewPager viewPager;
    private LinearLayout point_detail;
    private ArrayList<View> arrayList;
    private ListView passagelistView;
    //private List<Map<String,Object>> passagelist;
    private List<ImageListArray> passagelist;
    private MyPagerAdapter myPagerAdapter;
    private ScheduledExecutorService executor;
    private boolean isRunning = true;

    private Button testBtn;

    private int dpToPx(int dp) {
        //获取手机屏幕像素密度
        float phoneDensity = getResources().getDisplayMetrics().density;
        //加0.5f是为了四舍五入 避免丢失精度
        return (int) (dp * phoneDensity + 0.5f);

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 执行滑动到下一个页面
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                // 在发一个handler延时
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    //加载Fragment中的layout
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.main_fg,null);
        testBtn = view.findViewById(R.id.mental_test);
        viewPager=view.findViewById(R.id.view_pager);
        passagelistView=view.findViewById(R.id.passagelist);
        searchView = view.findViewById(R.id.searchView);
        arrayList=new ArrayList<>();
        arrayList.add(getLayoutInflater().inflate(R.layout.banner_1st_fg,null,false));
        arrayList.add(getLayoutInflater().inflate(R.layout.banner_2nd_fg,null,false));
        arrayList.add(getLayoutInflater().inflate(R.layout.banner_3rd_fg,null,false));
        myPagerAdapter=new MyPagerAdapter(arrayList);
        //初始化指示器
        initIndicatorView();
        //在初始化的时候 让指示器选中第一个位置
        updateIndicatorSelectState(0);
        //初始化ViewPager页面选择状态监听
        initPageChangeListener();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2
                - (Integer.MAX_VALUE / 2 % arrayList.size()));
        viewPager.setAdapter(myPagerAdapter);
        handler.sendEmptyMessageDelayed(0, 3000);
        passagelist=new ArrayList<>();
        fillData(passagelistView);
        setListener();
        return view;
    }

    public void setListener(){
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PaperList.class);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(view.getContext(), SearchPasage.class);
                intent.putExtra("key",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void initIndicatorView() {
        point_detail=view.findViewById(R.id.point_detail);

        for (int i = 0; i < arrayList.size(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpToPx(6), dpToPx(6));
            lp.leftMargin = dpToPx(10);
            lp.bottomMargin = dpToPx(6);

            View ivIndicator = new View(view.getContext());
            //[R.drawable.indicator_select]为指示器的背景资源 相关样式可替换
            ivIndicator.setBackgroundResource(R.drawable.indicator_select);
            ivIndicator.setLayoutParams(lp);
            ivIndicator.setEnabled(false);
            //将一个个指示器(ImageView)添加到父布局中
            point_detail.addView(ivIndicator);
        }

    }

    public void updateIndicatorSelectState(int position) {
        //此时传入的position还未经过处理 同样的需要对position进行取余数处理
        position = position % point_detail.getChildCount();
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

    //使用Okhttp访问网络
    public void fillData(View view){
        //String url=getString(R.string.serverurl)+"passage/allPassages";
        Log.d("fillData","进入方法");
        String url="http://172.20.10.10:8081/popmh/passage/allPassages";
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).get().build();
        Call call=okHttpClient.newCall(request);
        Log.d("call","使用call加入请求队列");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","call加入队列失败");
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body=response.body();
                String json= body.string();
                Log.d("JSON",json);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("JSON",json);
                        System.out.println("返回的数据"+json);
                        JSONArray passageArray= JSON.parseArray(json);
                        int size=passageArray.size();
                        for(int i=0;i<size;i++){
                            JSONObject passageObject=passageArray.getJSONObject(i);
                            //Map<String,Object> passage=new HashMap<>();
                            Long pid=passageObject.getLong("pid");
                            String ptitle=passageObject.getString("ptitle");
                            String ptime=passageObject.getString("ptime");
                            String fileName=passageObject.getString("fileName");
                            Integer collect_count = passageObject.getInteger("collect_count");
                            //Log.d("fileName",fileName);
                            ImageListArray imageListArray=new ImageListArray(pid,ptitle,ptime,fileName,collect_count);
                            passagelist.add(imageListArray);
                        }
                        ImageListAdapter imageListAdapter=new ImageListAdapter(view.getContext(),R.layout.passage_lv,passagelist);

                        passagelistView.setAdapter(imageListAdapter);
                        setListViewHeightBasedOnChildren(passagelistView,imageListAdapter);

                        passagelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if(AppInfo.personNow != null){
                                    String url="http://172.20.10.10:8081/popmh/passage/";
                                    StringBuilder stringBuilder = new StringBuilder(url);
                                    stringBuilder.append(passagelist.get(i).getPid().toString());
                                    Intent intent = new Intent(view.getContext(), passageDetail.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("key", stringBuilder.toString());
                                    bundle.putString("passageId", passagelist.get(i).getPid().toString());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(view.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView,ImageListAdapter imageListAdapter) {
        // 获取ListView对应的Adapter
        if (imageListAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = imageListAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = imageListAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (imageListAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
