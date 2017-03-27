package com.atguigu.mybilibili.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.mybilibili.R;


/**
 * Created by 李金桐 on 2017/2/28.
 * QQ: 474297694
 * 功能: xxxx
 */

public class AddSubView extends LinearLayout {
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private Context mContext;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public AddSubView(Context context) {
        this(context, null);
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_value = (TextView) findViewById(R.id.tv_value);
        tv_value.setText(value+"");
        initListener();
        setAttrs(attrs);

    }

    private void setAttrs(AttributeSet attrs) {
        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(mContext, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }
        }
    }

    private void initListener() {
        iv_sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value>minValue) {
                    tv_value.setText(--value+"");
                    if(l!=null) l.onNumberChenge(value);
                }
            }
        });
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value<maxValue) {
                    tv_value.setText(++value+"");
                    if(l!=null) l.onNumberChenge(value);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public   interface OnNumberChangeListener{
        void onNumberChenge(int number);
    }
    private  OnNumberChangeListener l;

    public void setOnNumberChangeListener(OnNumberChangeListener l) {
        this.l = l;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
