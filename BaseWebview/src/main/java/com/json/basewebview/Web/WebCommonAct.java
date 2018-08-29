package com.json.basewebview.Web;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.json.basewebview.R;


/**
 * 公共的webview的Activity
 */

public class WebCommonAct extends FragmentActivity {
    public static String FULL_PATH_URL = "FullPathURL";//全路径URL
    public static String Title_Bg = "Title_Bg";//头部背景
    public static String Back_Img = "Back_Img";//返回的图片
    private String fullURL;
    private int titleBg ;
    private int backImg ;
    public BaseTitleFrag mBaseTitleFrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web_common);
        fullURL = getIntent().getStringExtra(FULL_PATH_URL);
        titleBg =getIntent().getIntExtra(Title_Bg,-1);
        backImg =getIntent().getIntExtra(Back_Img,-1);
        mBaseTitleFrag = new BaseTitleFrag();
        if (!TextUtils.isEmpty(fullURL)) {
            mBaseTitleFrag.setLoadParams("hctou",fullURL,null,true);
        }
        if (titleBg!=-1&&backImg!=-1){
            setTitleBg(backImg,titleBg);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_web_common, mBaseTitleFrag).commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mBaseTitleFrag.commonNoTitleFrag.isMain) {
                mBaseTitleFrag.commonNoTitleFrag.webBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    //设置头部的背景图片
    public void setTitleBg(@DrawableRes int backImg, @DrawableRes int titleBg){
        mBaseTitleFrag.setTitleBg(backImg,titleBg);
    }

    public void setUserId(String userId){
        if (!TextUtils.isEmpty(userId)){
            mBaseTitleFrag.setUserId(userId);
        }
    }
}
