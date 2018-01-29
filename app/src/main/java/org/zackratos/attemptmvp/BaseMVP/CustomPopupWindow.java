package org.zackratos.attemptmvp.BaseMVP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.zackratos.attemptmvp.R;


/**
 * Created by Administrator on 2017/9/26.
 * 自定义底部弹出框
 */
public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {

    private Button btnTakePhoto, btnSelect, btnCancel;

    private View mPopView;

    private OnItemClickListener mListener;

    public CustomPopupWindow(Context context) {
        super(context);
        init(context);// 初始化布局
        setPopupWindow();// 设置窗口的相关属性
        // 设置点击属性
        btnTakePhoto.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.activity_boommenu, null);
        btnTakePhoto = (Button) mPopView.findViewById(R.id.id_btn_take_photo);
        btnSelect = (Button) mPopView.findViewById(R.id.id_btn_select);
        btnCancel = (Button) mPopView.findViewById(R.id.id_btn_cancelo);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }
}
