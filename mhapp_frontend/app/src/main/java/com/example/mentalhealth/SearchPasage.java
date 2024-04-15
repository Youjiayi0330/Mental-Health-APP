package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.ImageListAdapter;
import com.example.mentalhealth.entity.ImageListArray;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SearchPasage extends AppCompatActivity {

    private String queryTxt;
    private ListView passageListView;
    //private List<ImageListArray> passageList = new ArrayList<>();
    private SearchView searchView;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pasage);
        passageListView = findViewById(R.id.search_passageList);
        searchView = findViewById(R.id.search_searchView);
        backBtn = findViewById(R.id.back_home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        queryTxt = bundle.getString("key");
        searchView.setQuery(queryTxt,false);
        loadData();
        setListener();
    }

    public void setListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryTxt = query;
                loadData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPasage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadData(){
        List<ImageListArray> passageList = new ArrayList<>();
        String url = "http://172.20.10.10:8081/popmh/passage/findByName/"+queryTxt;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","搜索文章失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                passageListView.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray passageArray = JSON.parseArray(json);
                        for(int i=0; i<passageArray.size(); i++){
                            JSONObject passageObject = passageArray.getJSONObject(i);
                            Long pid=passageObject.getLong("pid");
                            String ptitle=passageObject.getString("ptitle");
                            String ptime=passageObject.getString("ptime");
                            String fileName=passageObject.getString("fileName");
                            Integer collect_count = passageObject.getInteger("collect_count");
                            Log.d("fileName",fileName);
                            ImageListArray imageListArray=new ImageListArray(pid,ptitle,ptime,fileName,collect_count);
                            passageList.add(imageListArray);
                        }
                        ImageListAdapter imageListAdapter = new ImageListAdapter(passageListView.getContext(),R.layout.passage_lv,passageList);
                        passageListView.setAdapter(imageListAdapter);
                        passageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                if(AppInfo.personNow != null){
                                    String url="http://172.20.10.10:8081/popmh/passage/";
                                    StringBuilder stringBuilder = new StringBuilder(url);
                                    stringBuilder.append(passageList.get(i).getPid().toString());
                                    Intent intent = new Intent(view.getContext(), passageDetail.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("key", stringBuilder.toString());
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
}