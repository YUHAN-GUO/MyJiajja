package com.base.gyh.baselib.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.base.gyh.baselib.annotation.Constant.LoadType.FRIST;
import static com.base.gyh.baselib.annotation.Constant.LoadType.LOADMORE;
import static com.base.gyh.baselib.annotation.Constant.LoadType.REFRESH;

/**
 * Created by GUOYH on 2019/5/28.
 */

@IntDef({FRIST,REFRESH,LOADMORE})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadType {

}