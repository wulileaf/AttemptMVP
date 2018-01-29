package org.zackratos.attemptmvp.BaseMVP;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by WuXian on 2017/9/1.
 * 基础Presenter类
 */
public class BasePresenter implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected boolean useEventBus = false;

    public BasePresenter() {
        onStart();
    }

    public void onStart() {
        if (useEventBus) {
            EventBus.getDefault().register(this);//注册eventbus
        }

    }

    @Override
    public void onDestroy() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this);//解除注册eventbus
        }
    }

    @Override
    public void showError(String error) {

    }

    /**
     * 是否使用eventBus,默认为使用(false)，
     *
     * @return
     */
    protected void useEventBus(boolean useEventBus) {
        this.useEventBus = useEventBus;
    }
}
