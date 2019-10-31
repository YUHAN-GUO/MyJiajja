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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.annotation.StateType;
import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.utils.RlvMangerUtil;
import com.base.gyh.baselib.widgets.dialog.pop.LoadingPop;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;


/*
 * created by taofu on 2018/11/28
 **/
public abstract class SupportFragment extends RxFragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;
    private BasePopupView loadingPop;
    private StateActivity mBaseStateActivity;
    private FragmentManager mFragmentManager;

    /**
     * 异常崩溃，但是没有完全杀死app，内存重启，保存状态
     *
     * @param outState bundle
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 加载的popuWindow
     */
    public void toggleLoading() {
        if (loadingPop == null) {
            loadingPop = new XPopup.Builder(getContext())
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(getContext()));
        }
        loadingPop.toggle();
    }

    public LinearLayoutManager getLinearLayoutManger() {
        return RlvMangerUtil.getInstance().getLinearLayoutManager(getActivity());
    }

    public GridLayoutManager getGridLayoutManager(int spanCount) {
        return RlvMangerUtil.getInstance().getGridLayoutManager(getActivity(), spanCount);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

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

    // 封装公共的添加Fragment的方法
    public void addFragment(Class<? extends SupportFragment> zClass, int layoutId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        String tag = zClass.getName();

        // 从 fragmentManager中查找这个fragment是否存在，
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        if (fragment != null) { // 如果存在就不用重新创建
            if (fragment.isAdded()) { // 如果 fragment 已经被添加
                if (fragment.isHidden()) { // 如果fragment 已经被添加，并且处于隐藏状态，那么显示
                    transaction.show(fragment); // 显示 fragment
                    hideOtherPage(transaction, fragment); // 隐藏其他fragment
                }
            } else { // 如果 fragment存在，但是没有被添加到activity，那么执行下面添加，（这种一般发生在activity 横竖屏切换）
                transaction.add(layoutId, fragment);
                hideOtherPage(transaction, fragment);
            }
        } else {
            // 如果没有从fragmentManager 中通过tag 找到fragment,那么创建一个新的fragment 实例
            try {
                fragment = zClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (fragment != null) {
                transaction.add(layoutId, fragment, tag);
                hideOtherPage(transaction, fragment);
            }
        }

        transaction.commit();
    }

    // 隐藏除了将要显示的fragment 以外的其他所有fragment
    private void hideOtherPage(FragmentTransaction transaction, Fragment willShowFragment) {

        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != willShowFragment) {
                transaction.hide(fragment);
            }
        }

    }

    public void startActivity(Class<? extends Activity> zClass, Bundle bundle) {
        if (mBaseStateActivity != null) {
            mBaseStateActivity.startActivity(zClass, bundle);
        }
    }

    public void startActivity(Class<? extends Activity> zClass) {
        if (mBaseStateActivity != null) {
            mBaseStateActivity.startActivity(zClass);
        }
    }

    public void startActivityForResult(Class<? extends Activity> zClass, int code) {
        startActivityForResult(new Intent(getContext(), zClass), code);

    }

    public void closeActivity() {

        if (mBaseStateActivity != null) {
            mBaseStateActivity.finish();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentManager = getChildFragmentManager();
        if (activity instanceof StateActivity) {
            mBaseStateActivity = (StateActivity) activity;
        }

    }

    /**
     * 异常崩溃后会再次走onCreate方法，这也就是为啥有时候fragment重叠，因为被创建多次
     * 发生Fragment重叠的根本原因在于FragmentState没有保存Fragment的显示状态，
     * 即mHidden，导致页面重启后，该值为默认的false，即show状态，所以导致了Fragment的重叠。
     * 两种方案：第一种在activity中处理，第二种在fragment中处理
     *
     * @param savedInstanceState bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //异常启动
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = null;
            if (getFragmentManager() != null) {
                ft = getFragmentManager().beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commit();
            }
        } else {
            //正常启动
        }
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
        mBaseStateActivity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancleOkhttpTag(this.getClass().toString());

    }
}
