package com.think.guoyh.ui.home;


import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.base.gyh.baselib.adapter.vpager.MyViewPagerAdapter;
import com.base.gyh.baselib.base.StateFragment;
import com.bumptech.glide.Glide;
import com.think.guoyh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends StateFragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initListener() {

    }
}
