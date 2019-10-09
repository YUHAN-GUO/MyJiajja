package com.base.gyh.baselib.widgets.dialog.pop;

import android.content.Context;
import android.support.annotation.NonNull;

import com.base.gyh.baselib.R;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * Created by GUO_YH on 2019/8/16 12:07
 */
public class LoadingPop extends CenterPopupView {
    public LoadingPop(@NonNull Context context) {
        super(context);
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
