package com.think.guoyh.ui.home;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.base.gyh.baselib.base.StateFragment;
import com.base.gyh.baselib.data.bean.ParamsBuilder;
import com.base.gyh.baselib.data.remote.okhttp.ModelSuperImpl;
import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.NetWorkListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.OnDownloadListener;
import com.base.gyh.baselib.utils.BaseSP;
import com.base.gyh.baselib.utils.MathUtils;
import com.base.gyh.baselib.utils.OpenFileUtils;
import com.base.gyh.baselib.utils.PermissUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.think.guoyh.R;
import com.think.guoyh.utils.AppStoreUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
