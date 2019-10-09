package com.base.gyh.baselib.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.base.gyh.baselib.annotation.Constant.StateType.EMPTY;
import static com.base.gyh.baselib.annotation.Constant.StateType.ERROR;
import static com.base.gyh.baselib.annotation.Constant.StateType.LOADING;
import static com.base.gyh.baselib.annotation.Constant.StateType.NORMAL;


/**
 * Created by GUO_YH on 2019/8/16 19:15
 */
@IntDef(value = {EMPTY, ERROR, LOADING, NORMAL})
@Retention(RetentionPolicy.SOURCE)
public  @interface StateType {

}
