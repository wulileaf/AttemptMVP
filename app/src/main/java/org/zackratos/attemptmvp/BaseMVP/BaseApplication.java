package org.zackratos.attemptmvp.BaseMVP;

import android.app.Application;
import android.content.Context;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
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
    private TableEntity entity;

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
    // 判断是否需要升级数据库
//    private void dbVersionControll(int oldVersion,int newVersion){
//        // 使用for实现跨版本升级数据库
//        for (int i = oldVersion; i < newVersion; i++) {
//            switch (i) {
//                case 1:{
//                    upgradeToVersion2(db);
//                }
//                break;
//                default:
//                    break;
//            }
//        }
//    }
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
//                db.addColumn(Sign.class,"test");// 添加新字段
//                db.dropTable(DeliverMailInfo.class);// 删除表
//                db.getTable().tableIsExist()// 创建表
//                alter table ss add(str1,int)//


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

    /**
     * 判断这个表是否存在 不存在就创建表
     */
//    public <T> void creatTable(DbManager db, String tableName, Class<T> tableBean) {
//        try {
//            if (!entity.tableIsExist()) { //!tabIsExist(db.getDatabase(), tableName)
//                SqlInfo sqlInfo;
//                sqlInfo = SqlInfoBuilder.buildCreateTableSqlInfo(db
//                        .getTable(tableBean));
//                db.execNonQuery(sqlInfo);
//            }
//        } catch (DbException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
