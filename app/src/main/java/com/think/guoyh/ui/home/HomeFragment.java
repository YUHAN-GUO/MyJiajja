package com.think.guoyh.ui.home;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.base.gyh.baselib.base.StateFragment;
import com.base.gyh.baselib.data.bean.ParamsBuilder;
import com.base.gyh.baselib.data.remote.okhttp.ModelSuperImpl;
import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.OnDownloadListener;
import com.base.gyh.baselib.utils.BaseSP;
import com.base.gyh.baselib.utils.MathUtils;
import com.base.gyh.baselib.utils.OpenFileUtils;
import com.base.gyh.baselib.utils.PermissUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.utils.AppStoreUtils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends StateFragment {
    private static int GANK_COMMAND = 99;
    private ViewHolder holder;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ok_down/demo/";
    private String fileName = "easyOk.apk";

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

    private void permissSuccess() {
        ModelSuperImpl.netWork().downApk(ParamsBuilder.build().path(path)
                .fileName(fileName).tag("downApk").resume(true), new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Logger.d("%s+++++++++++%s", "guoyh", "下载完成");
            }

            @Override
            public void onDownLoadTotal(long total) {
                holder.toal.setText(MathUtils.round((double) total / 1024 / 1024, 2) + "M");
                BaseSP.getInstance().setTotal(total);
            }

            @Override
            public void onDownloading(int progress) {
                Logger.d("%s++++++++++%s", "guoyh", progress);
                File file = new File(path, fileName);
                holder.apkInfo.setText("文件路径 ==> " + file.getAbsolutePath() + "\n" + "目前文件大小 ==> " + MathUtils.round((double) file.length() / 1024 / 1024, 2) + "M");
                holder.progressBar.setProgress(progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {

            }
        });
    }

    @Override
    protected void initView(View view) {
        holder = new ViewHolder(view);
        Logger.d("%s++++++++++++%s", "guoyh", "123111111");
//        ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().fragment(this), new IBaseHttpResultCallBack<String>() {
//            @Override
//            public void onSuccess(String data) {
//                Logger.d("%s++++++++++++++%s","guoyh1",data);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.d("%s++++++++++++%s","guoyh1",e.getMessage());
//            }
//        });

//        ModelSuperImpl.netWork().gankPost(ParamsBuilder.build().fragment(this),new IBaseHttpResultCallBack<String>(){
//            @Override
//            public void onSuccess(String data) {
//                Logger.d("%s+++++++++++++%s","guoyuhan",data);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.d("%s++++++++++++%s","guoyuhan",e.getMessage());
//            }
//        });
    }

    @Override
    protected void initListener() {
        holder.startDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissUtils.readWritePermiss(new PermissUtils.PermissCallBack() {
                    @Override
                    public void onSuccess() {
                        permissSuccess();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(getActivity(), "权限申请失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAndroidFile(new File(path, fileName));
            }
        });
        holder.toYing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appStoreIntent = AppStoreUtils.getAppStoreIntent();
                startActivity(appStoreIntent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(path, fileName);
                if (file.exists()) {
                    if (file.delete()) {
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        holder.progressBar.setProgress(0);
                        holder.apkInfo.setText("文件不存在");
                    }
                }
        }
        });
        holder.stopDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.getInstance().cancleOkhttpTag("downApk");
            }
        });
    }

    //这是打开文件的方式，并不是正确的安装apk。。
    public void openAndroidFile(File file) {
        if (!file.exists()) {
            Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file), OpenFileUtils.getMIMEType(file));//设置类型OpenFileUtils.getMIMEType(file)
        startActivity(intent);
    }
    private static class ViewHolder {
        public View rootView;
        public Button startDownLoad;
        public Button stopDownLoad;
        public ProgressBar progressBar;
        public TextView toal;
        public Button open;
        public Button delete;
        public TextView apkInfo;
        public Button toYing;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.startDownLoad = (Button) rootView.findViewById(R.id.start_downLoad);
            this.stopDownLoad = (Button) rootView.findViewById(R.id.stop_downLoad);
            this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            this.toal = (TextView) rootView.findViewById(R.id.toal);
            this.open = (Button) rootView.findViewById(R.id.open);
            this.delete = (Button) rootView.findViewById(R.id.delete);
            this.toYing = (Button) rootView.findViewById(R.id.toYing);
            this.apkInfo = (TextView) rootView.findViewById(R.id.apkInfo);
        }

    }
}
