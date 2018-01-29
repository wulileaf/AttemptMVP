package org.zackratos.attemptmvp.BaseMVP;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 * Activity管理器
 */
public class BaseActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    private static BaseActivityCollector instance;

    public synchronized static BaseActivityCollector getInstance() {
        if (null == instance) {
            instance = new BaseActivityCollector();
        }
        return instance;
    }

    /**
     * 添加Activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除Activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁Activity
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}
