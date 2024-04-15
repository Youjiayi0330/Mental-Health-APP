package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mentalhealth.fragment.CommunityFG;
import com.example.mentalhealth.fragment.ConsultFG;
import com.example.mentalhealth.fragment.MainFG;
import com.example.mentalhealth.fragment.MessageFG;
import com.example.mentalhealth.fragment.UserFG;
import com.example.mentalhealth.netty.AppInfo;

public class MainActivity extends AppCompatActivity {

    MainFG mainFG;
    ConsultFG consultFG;
    CommunityFG communityFG;
    MessageFG messageFG;
    UserFG userFG;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private void setShape(int drawableId, RadioButton radioButton) {

        //CirleDrawable cirleDrawable=new CircleDrawable()
        //定义底部标签图片大小和位置
        Drawable drawable_news = getResources().getDrawable(drawableId);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawableId);
        //CircleDrawable drawable = new CircleDrawable(getResources(), bitmap);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形  (这里的长和宽写死了 自己可以可以修改成 形参传入
        drawable_news.setBounds(0, 10, 130, 140);
        //设置图片在文字的哪个方向
        radioButton.setCompoundDrawables(null,drawable_news,null, null);
        //radioButton.setCompoundDrawablePadding(10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup=findViewById(R.id.radiogroup);
        RadioButton radioButton1=findViewById(R.id.button1);
        setShape(R.drawable.radio1,radioButton1);
        radioButton1.performClick();
        radioButton1.setTextColor(getResources().getColor(R.color.teal_700));
        RadioButton radioButton2=findViewById(R.id.button2);
        setShape(R.drawable.radio2,radioButton2);
        RadioButton radioButton3=findViewById(R.id.button3);
        setShape(R.drawable.radio3,radioButton3);
        RadioButton radioButton4=findViewById(R.id.button4);
        setShape(R.drawable.radio4,radioButton4);
        RadioButton radioButton5=findViewById(R.id.button5);
        setShape(R.drawable.radio5,radioButton5);

        mainFG = new MainFG();
        consultFG = new ConsultFG();
        communityFG = new CommunityFG();
        messageFG = new MessageFG();
        userFG = new UserFG();
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout,mainFG);
        fragmentTransaction.commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.button1:
                        fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,mainFG);
                        fragmentTransaction.commit();
                        radioButton1.setTextColor(getResources().getColor(R.color.teal_700));
                        radioButton2.setTextColor(getResources().getColor(R.color.black));
                        radioButton3.setTextColor(getResources().getColor(R.color.black));
                        radioButton4.setTextColor(getResources().getColor(R.color.black));
                        radioButton5.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case R.id.button2:
                        if(AppInfo.personNow == null){
                            Toast.makeText(MainActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                        } else if(AppInfo.personNow.getIdentity() == 0) {
                            Toast.makeText(MainActivity.this,"您是医生，无法咨询",Toast.LENGTH_SHORT).show();
                        } else {
                            fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout,consultFG);
                            fragmentTransaction.commit();
                            radioButton1.setTextColor(getResources().getColor(R.color.black));
                            radioButton2.setTextColor(getResources().getColor(R.color.teal_700));
                            radioButton3.setTextColor(getResources().getColor(R.color.black));
                            radioButton4.setTextColor(getResources().getColor(R.color.black));
                            radioButton5.setTextColor(getResources().getColor(R.color.black));
                        }
                        break;
                    case R.id.button3:
                        fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,communityFG);
                        fragmentTransaction.commit();
                        radioButton1.setTextColor(getResources().getColor(R.color.black));
                        radioButton2.setTextColor(getResources().getColor(R.color.black));
                        radioButton3.setTextColor(getResources().getColor(R.color.teal_700));
                        radioButton4.setTextColor(getResources().getColor(R.color.black));
                        radioButton5.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case R.id.button4:
                        fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,messageFG);
                        fragmentTransaction.commit();
                        radioButton1.setTextColor(getResources().getColor(R.color.black));
                        radioButton2.setTextColor(getResources().getColor(R.color.black));
                        radioButton3.setTextColor(getResources().getColor(R.color.black));
                        radioButton4.setTextColor(getResources().getColor(R.color.teal_700));
                        radioButton5.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case R.id.button5:
                        fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,userFG);
                        fragmentTransaction.commit();
                        radioButton1.setTextColor(getResources().getColor(R.color.black));
                        radioButton2.setTextColor(getResources().getColor(R.color.black));
                        radioButton3.setTextColor(getResources().getColor(R.color.black));
                        radioButton4.setTextColor(getResources().getColor(R.color.black));
                        radioButton5.setTextColor(getResources().getColor(R.color.teal_700));
                        break;
                }
            }
        });
    }
}