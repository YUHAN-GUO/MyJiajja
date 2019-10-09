package com.think.guoyh.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.base.gyh.baselib.base.SupportActivity;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.jaeger.library.StatusBarUtil;

/**
 *  与1 的区别：没有状态栏需要自己隔出距离 用于 顶部为ImageView 的Activity
 */
public class MyBaseActivity2 extends SupportActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }
    public void setStatusBar() {
        //true 为黑色
        StatusBarUtilTextColor.setStatusBarMode(this, false);
        StatusBarUtil.setTranslucentForImageView(this, 0,null);
    }

}
