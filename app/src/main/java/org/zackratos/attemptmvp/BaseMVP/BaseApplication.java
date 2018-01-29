package org.zackratos.attemptmvp.BaseMVP;

import android.app.Application;
import android.content.Context;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2017/9/4.
 * 初始化xUtils3
 * 添加okHttp
 * 初始化xUtils3数据库
 * 获取全局context
 */

public class BaseApplication extends Application {

    private static BaseApplication mContext;
    public static DbManager db;//初始化数据库管理器
    private DbManager.DaoConfig daoConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        ApplicationHelper.getInstance().getApplicationContext();

        initXutils();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();

//        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化xUtils3
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        LogUtil.customTagPrefix = "as";
        initXtilsDB();
    }

    /**
     * 初始化xUtils3数据库
     */
    private void initXtilsDB() {
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("AS")
                .setAllowTransaction(false).setDbOpenListener(new DbManager.DbOpenListener() {
            @Override
            public void onDbOpened(DbManager db) {
                // 开启WAL, 对写入加速提升巨大
                db.getDatabase().enableWriteAheadLogging();
            }
        }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            /*
             * 更新数据
             */
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        }).setDbVersion(1);
        db = x.getDb(daoConfig);
    }

    /**
     * 获取全局context
     */
    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getInstance() {
        return mContext;
    }
}
