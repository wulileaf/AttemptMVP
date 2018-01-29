package org.zackratos.attemptmvp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.zackratos.attemptmvp.BaseMVP.BaseActivity;
import org.zackratos.attemptmvp.BaseMVP.BaseTool;
import org.zackratos.attemptmvp.BaseMVP.IPresenter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    @BindView(R.id.tex_test)
//    TextView tex_test;

//    @BindView(R.id.img_test)
//    ImageView img_test;

    @BindView(R.id.btn)
    Button btn;

    @BindView(R.id.btn_sc)
    Button btn_sc;// 删除

    String[] name = {"tom", "andy", "marry"};

    String[] sex = {"女", "男", "女"};

    String[] age = {"10", "11", "12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        tex_test.setText("1111111");


        // 测试OK
//        StrictMode.setThreadPolicy(new
//                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//        StrictMode.setVmPolicy(
//                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
//
//        Bitmap bitmap = null;
//        try {
//            bitmap = new BaseTool().GetNetworkPictures("http://g.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c5452f6333c30292df5e0fe7ff8.jpg");
//            img_test.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // 测试NO
//        Bitmap bitmap = new BaseTool().GetLocalOrNetBitmap("http://g.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c5452f6333c30292df5e0fe7ff8.jpg");
//        img_test.setImageBitmap(bitmap);


        // 主要是用于快速加载网络图片
        // error下载源码加载资源3次无法显示图片的就使用默认图片显示(提高用户体验)
        // placeholder指的是加载过程中显示的图片
        // load是图片的URL地址，可以是图片的网络地址也可以是图片的本地文件地址
        // resize图片显示的大小
        // transfor图片显示出来的形状，如果想自定义的话可以选择集继承Transformation接口进行自定义(自定义学习可参照：https://www.zhihu.com/question/41101031,http://www.gcssloop.com/customview/Canvas_BasicGraphics)
        // into就是图片所放的控件
        // 测试OK
//        Picasso.with(this).load("http://g.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c5452f6333c30292df5e0fe7ff8.jpg").error(R.drawable.alert_dialog_icon).resize(200, 200).transform(new CropSquareTransformation()).into(img_test);


        // 测试数据存储
        // 测试OK
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestDao testDao = new TestDao();
                for (int i = 0; i < name.length; i++) {
                    TestBean testBean = new TestBean();
                    testBean.setName(name[i]);
                    testBean.setSex(sex[i]);
                    testBean.setAge(age[i]);
                    testDao.saveTestBean(testBean);
                }
                Toast.makeText(MainActivity.this, "全部保存成功", Toast.LENGTH_LONG).show();

            }
        });
        // 数据库测试删除数据
        // 测试OK
        btn_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestDao testDao = new TestDao();
                testDao.deletedata(2);
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

//    @Override
//    protected void initData() {
//        tex_test.setText("1111111");
//    }
//
//    @Override
//    protected int initView() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected IPresenter getPresenter() {
//        return null;
//    }
//
//    @Override
//    protected Intent mainIntent(Context context) {
//        return null;
//    }
}
