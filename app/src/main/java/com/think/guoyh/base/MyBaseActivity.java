package com.think.guoyh.base;

import android.support.v4.content.ContextCompat;

import com.base.gyh.baselib.base.SupportActivity;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.jaeger.library.StatusBarUtil;
import com.think.guoyh.R;


/**
 * Created by GUO_YH on 2019/9/18 09:32
 * 根据项目需求 对一些操作统一管理
 */
public class MyBaseActivity extends SupportActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }
    public void setStatusBar() {
        //true 为黑色
        StatusBarUtilTextColor.setStatusBarMode(this, true);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.color_blue));
    }
}
