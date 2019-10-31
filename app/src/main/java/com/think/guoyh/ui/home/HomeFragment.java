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
    private void openXiangCe() {
        int themeId = R.style.picture_default_style;
        PictureSelector.create(HomeFragment.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(false ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
//                .previewVideo(true)// 是否可预览视频
//                .enablePreviewAudio(true) // 是否可播放音频
//                .isCamera(true)// 是否显示拍照按钮
//                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                        .enableCrop(cb_crop.isChecked())// 是否裁剪
//                        .compress(cb_compress.isChecked())// 是否压缩
//                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                        //.compressSavePath(getPath())//压缩图片保存地址
//                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                        .openClickSound(cb_voice.isChecked())// 是否开启点击声音
//                .selectionMedia(selectList)// 是否传入已选图片
//                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
////                        .videoMaxSecond(15)
////                        .videoMinSecond(10)
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
//                        .minimumCompressSize(100)// 小于100kb的图片不压缩
//                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                        //.rotateEnabled(true) // 裁剪是否可旋转图片
//                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
//                        //.videoQuality()// 视频录制质量 0 or 1
//                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
//                        //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PictureConfig.CHOOSE_REQUEST){
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            List<String> imgList = new ArrayList<>();
            for (int i = 0; i < localMedia.size(); i++) {
                Logger.d("%s+++++++++++++%s", "guoyh", localMedia.get(i).getPath());
                imgList.add(localMedia.get(i).getPath());
            }
            toShangChuan(imgList);
        }
    }

    private void toShangChuan(List<String> imgList) {
        File file = new File(imgList.get(0));
        Pair<String, File> map = new Pair<>("file", file);
        ModelSuperImpl.netWork().uploadPic(ParamsBuilder.build(), new NetWorkListener() {
            @Override
            public void onNetCallBack(int command, Object object) {

            }
        },map);
    }

    private void permissSuccess() {
        openXiangCe();
//        ModelSuperImpl.netWork().downApk(ParamsBuilder.build().path(path)
//                .fileName(fileName).tag("downApk").resume(true), new OnDownloadListener() {
//            @Override
//            public void onDownloadSuccess(File file) {
//                Logger.d("%s+++++++++++%s", "guoyh", "下载完成");
//            }
//
//            @Override
//            public void onDownLoadTotal(long total) {
//                holder.toal.setText(MathUtils.round((double) total / 1024 / 1024, 2) + "M");
//                BaseSP.getInstance().setTotal(total);
//            }
//
//            @Override
//            public void onDownloading(int progress) {
//                Logger.d("%s++++++++++%s", "guoyh", progress);
//                File file = new File(path, fileName);
//                holder.apkInfo.setText("文件路径 ==> " + file.getAbsolutePath() + "\n" + "目前文件大小 ==> " + MathUtils.round((double) file.length() / 1024 / 1024, 2) + "M");
//                holder.progressBar.setProgress(progress);
//            }
//
//            @Override
//            public void onDownloadFailed(Exception e) {
//
//            }
//        });
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
