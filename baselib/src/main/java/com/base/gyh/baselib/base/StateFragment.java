package com.base.gyh.baselib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.base.gyh.baselib.R;
import com.base.gyh.baselib.annotation.StateType;
import com.base.gyh.baselib.widgets.dialog.pop.LoadingPop;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.trello.rxlifecycle2.components.support.RxFragment;
import java.util.List;
import static com.base.gyh.baselib.annotation.StateType.EMPTY;
import static com.base.gyh.baselib.annotation.StateType.ERROR;
import static com.base.gyh.baselib.annotation.StateType.LOADING;
import static com.base.gyh.baselib.annotation.StateType.NORMAL;

/*
 * created by taofu on 2018/11/28
 **/
public abstract class StateFragment extends SupportFragment {
    public ReloadAndEmptyListener reloadAndEmptyListener;
    private View view;
    private LinearLayout mRootBaseView;//根布局
    private NetStateLayout netStateLayout;//状态布局
    private MyToolbar myToolbar;//Toolbar


    /**
     * 获取到子布局
     *
     * @param view view
     */
    private void initBaseView(View view) {
        mRootBaseView = view.findViewById(R.id.activity_base_root);
        netStateLayout = view.findViewById(R.id.netStateLayout);
        myToolbar = view.findViewById(R.id.activity_base_toolBar);
        myToolbar.setVisibility(View.GONE);
    }

    /**
     * @param titleBarIsShow titleBar是否显示
     * @param tvLeftIsShow   返回键是否显示
     * @param title          中间的文字
     */
    public void initTitleBar(boolean titleBarIsShow, boolean tvLeftIsShow,
                             String title) {
        if (titleBarIsShow == true) {
            if (!tvLeftIsShow) {
                myToolbar.hideBack(true);
            } else {
                myToolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
                    @Override
                    public void onClick(int type) {
                        if (type == MyToolbar.BACK) {
//                            finish();
                        }
                    }
                });
            }
            myToolbar.setTitleText(title);
        } else {
            myToolbar.setVisibility(View.GONE);
        }
    }


    /**
     * 切换页面的布局
     *
     * @param stateType 页面状态 NORMAL  EMPTY  ERROR LOADING
     */
    public void changePageState(@StateType int stateType) {
        if (netStateLayout.getVisibility() == View.GONE) {
            netStateLayout.setVisibility(View.VISIBLE);
        }
        if (stateType == NORMAL) {
            mRootBaseView.setVisibility(View.VISIBLE);
        } else {
            mRootBaseView.setVisibility(View.GONE);

        }
        switch (stateType) {
            case NORMAL:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_HIDE);
                break;
            case ERROR:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_ERROR);
                break;
            case EMPTY:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_EMPTY);
                break;
            case LOADING:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_LOADING);
                break;
        }


    }

    public void setReloadInterface(ReloadAndEmptyListener reloadAndEmptyListener) {
        this.reloadAndEmptyListener = reloadAndEmptyListener;
    }

    public View initContentView(int layoutId) {
        View view = LayoutInflater.from(mRootBaseView.getContext()).inflate(layoutId, mRootBaseView, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (null != mRootBaseView) {
            mRootBaseView.addView(view, lp);
        }
        return view;
    }


    private void initMyListener() {
        netStateLayout.setOnEmptyAndErrorRetryClickListener(new NetStateLayout.OnEmptyAndErrorRetryClickListener() {
            @Override
            public void onEmptyRetryClicked() {
                reloadAndEmptyListener.emptyClickListener();
            }

            @Override
            public void onErrorRetryClicked() {
                reloadAndEmptyListener.reloadClickListener();
            }
        });
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData();
            isDataInitiated = true;
        }
    }

    /**
     * 懒加载
     */
    protected abstract void loadData();
    protected abstract void initView(View view);
    protected abstract void initListener();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_base, container, false);
            view.setClickable(true);
        }
        //找到State布局
        initBaseView(view);
        //将布局加到State布局中
        View view = initContentView(getLayoutRes());
        //提供View 相关操作的方法
        initView(view);
        //取消ToolBar 展示  可以在子类中打开
        initTitleBar(false,false,"");
        //初始化 Error 和Empty的 布局点击监听
        //提供监听方法
        initListener();
        return this.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        //加载数据
        prepareFetchData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.view = null;
    }

    public interface ReloadAndEmptyListener {
        void reloadClickListener();

        void emptyClickListener();
    }
}
