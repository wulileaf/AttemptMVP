package org.zackratos.attemptmvp.BaseMVP;

/**
 * Created by leaf on 2017/9/1.
 * 请求接口成功或者失败的方法接口
 */

public interface IDataRequestListener {
    void loadSuccess(Object object);

    void loadError(Object object);
}
