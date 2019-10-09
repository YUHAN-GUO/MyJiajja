package com.base.gyh.baselib.annotation;

/**
 * Created by GUOYH on 2019/5/28.
 */
public interface Constant {
    interface LoadType{
        int FRIST = 0x10;
        int REFRESH = 0x20;
        int LOADMORE = 0x40;
    }
    interface  StateType{
         int NORMAL = 0x100;//数据内容显示正常
         int EMPTY = 0x200;//数据内容显示正常
         int ERROR = 0x400;//数据加载失败
         int LOADING = 0x800;//数据加载中
    }
}
