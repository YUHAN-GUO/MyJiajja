package com.think.guoyh.base;

import com.base.gyh.baselib.base.activity.ViewPagerFragmentStateActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by GUO_YH on 2019/9/3 16:37
 */
public class MainStateActivity extends ViewPagerFragmentStateActivity {

    @Override
    protected void initFTs(List<FT> fts) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0,null);

//        StatusBarUtil.setTranslucent(this,0);
    }

    @Override
    protected int initMpos() {
        return 0;
    }

}
