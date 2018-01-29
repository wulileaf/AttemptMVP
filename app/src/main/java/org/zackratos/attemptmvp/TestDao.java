package org.zackratos.attemptmvp;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.zackratos.attemptmvp.BaseMVP.BaseApplication;

/**
 * Created by Administrator on 2018/1/9.
 */
public class TestDao {

    /**
     * 创建表单
     * 存入数据
     */
    public synchronized void saveTestBean(TestBean testBean) {

        try {
            BaseApplication.db.saveBindingId(testBean);
            BaseApplication.db.getDatabase().execSQL("VACUUM;");// 合并
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表单
     */
    public synchronized void deleteTestBean() {
        try {
            if (BaseApplication.db.getTable(TestBean.class).tableIsExist()) {// 判断表存不存在
                BaseApplication.db.delete(TestBean.class);// 删除这张表
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void deletedata(int id) {
        try {
            BaseApplication.db.delete(TestBean.class, WhereBuilder.b("id", "=", id));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


}
