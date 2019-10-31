package com.base.gyh.baselib.widgets.dialog.pop;

import android.content.Context;
import android.support.annotation.NonNull;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.base.SupportActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * Created by GUO_YH on 2019/8/16 12:07
 */
public class LoadingPop extends CenterPopupView {

    private static BasePopupView loadingPop;

    public LoadingPop(@NonNull Context context) {
        super(context);
    }
    public static void showLoad(Context context){
        if (loadingPop == null) {
            // 按返回键是否关闭弹窗，默认为true 点击外部是否关闭弹窗，默认为true
            loadingPop = new XPopup.Builder(context)
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(context));
        }
        loadingPop.show();
    }
    public static void hideLoad(Context context){
        if (loadingPop == null) {
            // 按返回键是否关闭弹窗，默认为true 点击外部是否关闭弹窗，默认为true
            loadingPop = new XPopup.Builder(context)
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(context));
        }
        loadingPop.dismiss();
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.loading_pop;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
