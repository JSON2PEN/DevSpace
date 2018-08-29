package com.json.basewebview.Web;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.json.basewebview.R;
import com.json.basewebview.Utils.MyToast;
import com.json.basewebview.Utils.SteepStatusBarUtils;
import com.json.basewebview.Utils.UICallBack.UpdateBack;
import com.json.basewebview.Utils.UpdateUI;
import com.json.basewebview.bean.MenuShowBean;
import com.json.basewebview.bean.ShareBean;
import com.json.basewebview.widgets.MenuListviewWindow;

import java.util.List;


/**
 * 在无头布局的基础上,增加公共的头布局
 */

public class BaseTitleFrag extends Fragment implements CommonJsInterface.CommonJsCallBack,MenuListviewWindow.MenuJSCode,CommonNoTitleFrag.JuiceMain{
    private String jsName;
    private String loadUrl;
    private boolean showBack =false;//home页面是否展示back,默认不展示
    public View mRootView;
    public View buttomView;
    public CommonNoTitleFrag commonNoTitleFrag;
    public Activity mActivity;
    public FrameLayout flBack;
    public TextView tvWebTile;
    public FrameLayout flRightIcon;
    public FrameLayout flTitle;
    public ImageView ivRightIcon;
    public CommonJsInterface jsInterface;
    public MenuListviewWindow newPop;
    private boolean isSteep =false;//是否为沉浸式状态栏
    public ImageView ivTitleLeft;
    public FrameLayout flRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_base_title,container,false);
        mActivity = getActivity();
        initView();
        initFragment();
        if (isSteep){
            SteepStatusBarUtils.setImmerseLayout(flRoot,mActivity);
        }
        return mRootView;
    }

    private void initView() {
        flBack = findView(R.id.fl_back);
        tvWebTile = findView(R.id.tv_web_title);
        flRightIcon = findView(R.id.fl_right_icon);
        flTitle = findView(R.id.fl_title);
        ivRightIcon = findView(R.id.iv_right_icon);
        ivTitleLeft = findView(R.id.iv_title_left);
        flRoot = findView(R.id.fl_root);
        flBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonNoTitleFrag.isMain) {
                    mActivity.finish();
                } else {
                    commonNoTitleFrag.webBack();
                }
            }
        });
        if (showBack){
            flBack.setVisibility(View.VISIBLE);
        }
    }

    private void initFragment() {
        commonNoTitleFrag = new CommonNoTitleFrag();
        jsInterface = new CommonJsInterface(commonNoTitleFrag,this);
        commonNoTitleFrag.setParams(jsName,loadUrl, jsInterface,this);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_frag_content, commonNoTitleFrag).commitAllowingStateLoss();
    }

    /**
     * 必须执行的方法
     * @param jsName
     * @param loadUrl
     */
    public void setLoadParams(String jsName,String loadUrl,@Nullable View buttomView,boolean isSteep){
        this.jsName=jsName;
        this.loadUrl=loadUrl;
        this.buttomView =buttomView;
        this.isSteep =isSteep;
    }
    private String userId;
    public void setUserId(String userId){
        this.userId =userId;
    }
    public void setShowBack(boolean showBack){
        this.showBack =showBack;
    }
    protected <T extends View> T findView(int id) {
        return (T) mRootView.findViewById(id);
    }

    @Override
    public void setWebTitle(final String title) {
        UpdateUI.doOnUI(() -> tvWebTile.setText(title));
    }

    @Override
    public String setUserId() {
        return userId;
    }

    @Override
    public String setMode() {
        return null;
    }

    @Override
    public void loadJsCode(final String jsCode) {
        UpdateUI.doOnUI(() -> commonNoTitleFrag.mWebView.loadUrl("javascript:" + jsCode));

    }

    @Override
    public void doShare(ShareBean shareBean) {
        MyToast.showToast("执行分享");
    }

    @Override
    public void showButton(final int buttonType,final String buttonClickFunction) {
        UpdateUI.doOnUI(() -> {
                switch (buttonType) {
                    case 0://不显示任何
                        flRightIcon.setVisibility(View.GONE);
                        break;
                    case 1://分享按钮
                        flRightIcon.setVisibility(View.VISIBLE);
                        ivRightIcon.setImageResource(R.drawable.pic_menu_share);
                        flRightIcon.setOnClickListener(v -> loadJsCode(buttonClickFunction));
                        break;
                    case 2://菜单按钮
                        flRightIcon.setVisibility(View.VISIBLE);
                        ivRightIcon.setImageResource(R.drawable.pic_menu);
                        flRightIcon.setOnClickListener(v -> showMenuButon(buttonClickFunction));
                        break;
                }
        });
    }
    @Override
    public void login() {

    }
    //显示菜单
    public void showMenuButon(final String buttonClickFunction) {
        List<MenuShowBean> menuShowBeen = new Gson().fromJson(buttonClickFunction, new TypeToken<List<MenuShowBean>>() {
        }.getType());
        if (menuShowBeen == null) {
            Toast.makeText(mActivity,"获取网络数据错误",Toast.LENGTH_SHORT).show();
            return;
        }
        //适应性填充
        newPop = MenuListviewWindow.newInstance();
        newPop.showFourOption(mActivity, ivRightIcon, menuShowBeen, this);
    }

    @Override
    public void codeBack(String code) {
        loadJsCode(code);
        if (newPop != null) {
            newPop.relase();
        }
    }

    public void reloadUrl() {
        if (commonNoTitleFrag!=null)
        commonNoTitleFrag.reloadUrl();
    }

    public boolean isHome(){
        return commonNoTitleFrag==null?true:commonNoTitleFrag.isMain;
    }
    public void goBack(){
        if (commonNoTitleFrag!=null){
            commonNoTitleFrag.webBack();
        }
    }
    //设置头部的背景图片
    public void setTitleBg(@DrawableRes int backImg,@DrawableRes int titleBg){
        ivTitleLeft.setImageDrawable(getResources().getDrawable(backImg));
        flRoot.setBackground(getResources().getDrawable(titleBg));
    }

    @Override
    public void isMain(boolean isMain) {
        UpdateUI.doOnUI(() -> {
            if (isMain) {
                if (buttomView != null) {
                    buttomView.setVisibility(View.VISIBLE);
                }
                if (!showBack){
                    flBack.setVisibility(View.GONE);
                }
            } else {
                if (buttomView != null) {
                    buttomView.setVisibility(View.GONE);
                }
                if (!showBack){
                    flBack.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    @Override
    public void setTitleBg(String color) {
        UpdateUI.doOnUI(()->{
            flRoot.setBackgroundColor(Color.parseColor(color));
            commonNoTitleFrag.setTitleStyle(1);
        });
    }
    @Override
    public void resetTitleBg() {

    }
}
