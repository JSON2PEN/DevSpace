package com.jone.devspace;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.json.basewebview.widgets.CustomToast;
import com.json.basewebview.widgets.bannerViewPager.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public BannerViewPager bannerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvHello=findViewById(R.id.tv_hello);
        bannerViewPager =findViewById(R.id.banner);
        List<Integer> mImgs =new ArrayList<>();
        List<View> mIvs =new ArrayList<>();
        mImgs.add(R.drawable.a_answer);
        mImgs.add(R.drawable.a_detail);
        mImgs.add(R.drawable.a_own);
        mImgs.add(R.drawable.a_publish);
        ViewGroup.LayoutParams layoutParams = bannerViewPager.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        for (int i = 0; i < mImgs.size(); i++) {
            ImageView iv =new ImageView(this);
            iv.setImageResource(mImgs.get(i));
            iv.setLayoutParams(layoutParams);
            mIvs.add(iv);
        }
        bannerViewPager.setIndicatorColor(Color.parseColor("#ffff00"),Color.parseColor("#0000ff"));
        bannerViewPager.setIndexMarginBottom(200);
        bannerViewPager.setData(mIvs);
        tvHello.setOnClickListener((view)->{
            CustomToast.showToast("执行了");
        });
    }
}
