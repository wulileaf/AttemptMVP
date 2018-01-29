package org.zackratos.attemptmvp.BaseMVP;

/**
 * Created by Administrator on 2017/10/31.
 * 为EventBus传递信息使用
 */
public class BaseEventBusMessage {

    private String mMsg;

    public BaseEventBusMessage(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
