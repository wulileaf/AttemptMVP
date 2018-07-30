package org.zackratos.attemptmvp.BaseMVP;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.attemptmvp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.xutils.x.isDebug;


/**
 * Created by Administrator on 2017/8/15.
 * 基础Activity类
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity {

    protected P mPresenter;
    private Dialog dialog;// 添加提示框
    private Unbinder mUnbinder;// 注解
    private boolean useEventBus = false;// EventBus
    public TextView tv_titleContent, tv_titleback, tv_titleright;// 公共头部

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());

        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(getApplication()); // 尽可能早，推荐在Application中初始化

        BaseActivityCollector.getInstance().addActivity(this);// 初始化Activity管理类，方便销毁
        mUnbinder = ButterKnife.bind(this);// 绑定Activity
        mPresenter = getPresenter();
        initData();
        if (useEventBus) {
            EventBus.getDefault().register(this);// 注册EventBus
        }
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     */
    protected abstract int initView();

    /**
     * 显示ProgressDialog
     */
    public void showProgressDialog(String message, Boolean yrn) {
        dialog = CustomProgressDialog.createLoadingDialog(this, message);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐司提示
     * 显示位置在页面的中间位置
     */
    public void showMidToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 吐司提示
     * 显示图片加文字的提示方式
     * 原生的
     * content:要显示的文字
     * color：要显示文字的颜色
     * mipmap：图片
     * dt: 图示显示的时间长短
     */
    public void showPhotoToast(String content, int color, int mipmap, int dt) {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(dt);
        toast.setGravity(Gravity.CENTER, 0, 0);// 后面的参数是标识吐司提示在页面的位置

//==========================================1==============================================================
//        LinearLayout toastView = (LinearLayout) toast.getView();
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextColor(Color.WHITE);
//        v.setTextSize(18);
//        ImageView imageCodeProject = new ImageView(getApplicationContext());
//        imageCodeProject.setImageResource(R.mipmap.s);
//        toastView.addView(imageCodeProject, 0);
//        toast.show();
//==========================================1==============================================================

//==========================================2==============================================================
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_custom, null);
        TextView text1 = (TextView) view.findViewById(R.id.ez);
        text1.setText(content);
        text1.setTextColor(getResources().getColor(color));
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        imageView.setImageResource(mipmap);// mipmap图片大小(>70*70)
        toast.setView(view);
        toast.show();
//==========================================2==============================================================
    }

    /**
     * 弹出式提示框
     */
    public void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", null).show();
    }

    /**
     * onDestroy方法执行的操作
     * 解除注解
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityCollector.removeActivity(this);// 关闭这个Activity管理类
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        if (useEventBus) {
            EventBus.getDefault().unregister(this);//解除注册EventBus
        }
        this.mUnbinder = null;
        this.mUnbinder = null;
    }

    /**
     * 是否使用EventBus,默认为使用(false)，
     */
    protected void useEventBus(boolean useEventBus) {
        this.useEventBus = useEventBus;
    }

    /**
     * 公共的头部
     */
    public void initTitleBar() {
        tv_titleContent = (TextView) findViewById(R.id.title_content);
        tv_titleright = (TextView) findViewById(R.id.title_right);
        tv_titleback = (TextView) findViewById(R.id.title_back);
        tv_titleback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    protected abstract P getPresenter();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = getPresenter();
        }
    }

    /**
     * 布局切换  ok
     */
    protected void replace(@IdRes int fragmentId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .commit();
    }

    protected void replaceWithStack(@IdRes int fragmentId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 跳转
     */
    protected abstract Intent mainIntent(Context context);


}
