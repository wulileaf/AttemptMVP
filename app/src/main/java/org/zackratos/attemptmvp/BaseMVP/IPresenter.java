package org.zackratos.attemptmvp.BaseMVP;

/**
 * Created by Administrator on 2017/9/4.
 * IPresenter接口
 */

public interface IPresenter {
    void onStart();
    void onDestroy();
    void showError(String error);
}
