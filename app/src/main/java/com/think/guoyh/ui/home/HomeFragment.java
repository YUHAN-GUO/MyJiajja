package com.think.guoyh.ui.home;


import android.support.v4.app.Fragment;
import android.view.View;

import com.base.gyh.baselib.base.fragment.StateFragment;
import com.think.guoyh.R;
import com.think.guoyh.base.MyStateFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends MyStateFragment {

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


    @Override
    public void onEmptyRetryClicked() {

    }

    @Override
    public void onErrorRetryClicked() {

    }
}
