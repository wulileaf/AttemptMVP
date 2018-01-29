package org.zackratos.attemptmvp.BaseMVP;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.zackratos.attemptmvp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/15.
 * 基础Fragment类
 */

public abstract class BaseFragment<P extends IPresenter> extends Fragment {

    protected View mRootView;
    protected P mPresenter;
    private Unbinder mUnbinder;
    private boolean useEventBus = false;
    private Dialog dialog;// 添加提示框
    public Activity mActivity;
    public TextView tv_titleContent, tv_titleback, tv_titleright;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView(inflater, container);
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus) {
//            EventBus.getDefault().register(this);//注册eventbus
        }
        initData();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = getPresenter();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();//解绑butterknife
        }
        if (useEventBus) {
//            EventBus.getDefault().unregister(this);//解除注册eventbus
        }
        this.mPresenter = null;
        this.mRootView = null;
        this.mUnbinder = null;
    }

    /**
     * 是否使用eventBus,默认为使用(false)，
     *
     * @return
     */
    protected void useEventBus(boolean useEventBus) {
        this.useEventBus = useEventBus;
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract P getPresenter();

    /**
     * 显示ProgressDialog
     */
    public void showProgressDialog(String message, Boolean yrn) {
        dialog = CustomProgressDialog.createLoadingDialog(mActivity, message);
        dialog.setCancelable(yrn);
        dialog.show();
    }

    /**
     * 关闭ProgressDialog
     */
    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 吐司提示
     */
    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 布局切换  ok
     */
    protected void replace(@IdRes int fragmentId, Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .commit();
    }


    protected void replaceWithStack(@IdRes int fragmentId, Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 公共的头部
     */
    public void initTitleBar() {
        tv_titleContent = (TextView) getActivity().findViewById(R.id.title_content);
        tv_titleright = (TextView) getActivity().findViewById(R.id.title_right);
        tv_titleback = (TextView) getActivity().findViewById(R.id.title_back);
        tv_titleback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getActivity().finish();
            }
        });
    }
}
