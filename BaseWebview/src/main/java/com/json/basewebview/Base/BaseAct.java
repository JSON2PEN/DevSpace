package com.json.basewebview.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.json.basewebview.Utils.RxBus.RxBus;

/**
 * 基类activity
 */

public abstract class BaseAct extends FragmentActivity{
    private boolean shouldRegister =false;
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.shouldRegister=setShouldRegister();
        if (shouldRegister){
            RxBus.get().register(this);
        }
        ActManger.getInstance().pushAct(this);
    }
    protected abstract boolean setShouldRegister();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shouldRegister){
            RxBus.get().unRegister(this);
        }
        ActManger.getInstance().removeAct(this);
    }
}
