package com.json.basewebview.Web;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import com.json.basewebview.Utils.UICallBack.UpdateBack;
import com.json.basewebview.Utils.UpdateUI;
import com.json.basewebview.Web.Base.BaseNoTitleWebFragment;


/**
 * 新版抽离出来的嵌套不带头部的基类fragment
 */

public class CommonNoTitleFrag extends BaseNoTitleWebFragment<CommonJsInterface> {
    private String jsName;
    private String loadUrl;
    private CommonJsInterface commonJsInterface;
    private JuiceMain juiceMain;
    private int titleStyle;//0 代表默认 1代表黑色版

    @Override
    public CommonJsInterface getJsInterface() {
        return commonJsInterface;
    }

    @Override
    public String getJsName() {
        return jsName;
    }

    @Override
    public String setWebUrl() {
        return loadUrl;
    }

    @Override
    public boolean beforeBack() {
        return false;
    }

    public void setParams(String jsName, String loadUrl, CommonJsInterface commonJsInterface, JuiceMain juiceMain) {
        this.jsName = jsName;
        this.loadUrl = loadUrl;
        this.commonJsInterface = commonJsInterface;
        this.juiceMain =juiceMain;
    }
    public interface JuiceMain{
        void isMain(boolean isMain);
        void resetTitleBg();
    }

    @Override
    public void isMain(boolean isMain) {
        juiceMain.isMain(isMain);
    }

    @Override
    public void beforeLoad(WebView view, String url) {
        resetBG();
    }

    @Override
    public void successsBack() {
        resetBG();
    }
    public void setTitleStyle(int titleStyle) {
        this.titleStyle =titleStyle;
    }
    //回复成默认的头部
    private void resetBG() {
        if (titleStyle == 1) {
            juiceMain.resetTitleBg();
            titleStyle =0;
        }
    }

}
