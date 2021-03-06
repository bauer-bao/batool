package com.babase.lib.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babase.lib.R;

/**
 * @author bauer_bao on 17/6/6.
 */

public class BaEmptyErrorView extends LinearLayout {
    private ImageView widgetEmptyErrorIv;
    private TextView widgetEmptyErrorTv, widgetEmptyErrorBtn;
    private ConstraintLayout widgetEmptyErrorView;

    private Context context;

    private OnSceenClickListener clickListener;

    /**
     * 默认不显示按钮
     */
    private boolean showBtn = false;

    public BaEmptyErrorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        initView();
    }

    public BaEmptyErrorView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        inflate(context, R.layout.widget_ba_empty_error_view, this);
        widgetEmptyErrorIv = findViewById(R.id.widget_empty_error_iv);
        widgetEmptyErrorTv = findViewById(R.id.widget_empty_error_tv);
        widgetEmptyErrorBtn = findViewById(R.id.widget_empty_error_btn);
        widgetEmptyErrorView = findViewById(R.id.widget_empty_error_view);

        widgetEmptyErrorView.setOnClickListener(view -> {
            if (clickListener != null && !showBtn) {
                clickListener.onEevClick(false);
            }
        });

        widgetEmptyErrorBtn.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onEevClick(true);
            }
        });
    }

    public void setClickListener(OnSceenClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 是否显示按钮
     *
     * @param showBtn
     */
    public void showBtn(boolean showBtn) {
        this.showBtn = showBtn;
        widgetEmptyErrorBtn.setVisibility(showBtn ? VISIBLE : GONE);
    }

    /**
     * 设置数据
     *
     * @param messageId
     * @param drawableId
     */
    public void initData(@StringRes int messageId, @DrawableRes int drawableId) {
        widgetEmptyErrorTv.setText(messageId);
        widgetEmptyErrorIv.setImageResource(drawableId);
    }

    /**
     * 设置数据
     *
     * @param messageStr
     * @param drawableId
     */
    public void initData(String messageStr, @DrawableRes int drawableId) {
        widgetEmptyErrorTv.setText(messageStr);
        widgetEmptyErrorIv.setImageResource(drawableId);
    }

    /**
     * 图片是否可见
     *
     * @param visible
     */
    public void setImageVisible(int visible) {
        widgetEmptyErrorIv.setVisibility(visible);
    }

    /**
     * 设置按钮颜色，文字，背景色
     *
     * @param btnMsg
     * @param btnTxtColor
     * @param btnBackground
     */
    public void setBtn(String btnMsg, @ColorRes int btnTxtColor, @DrawableRes int btnBackground) {
        widgetEmptyErrorBtn.setText(btnMsg);
        widgetEmptyErrorBtn.setTextColor(ContextCompat.getColor(context, btnTxtColor));
        widgetEmptyErrorBtn.setBackgroundResource(btnBackground);
    }

    /**
     * 设置颜色
     *
     * @param backgroundColor
     * @param textColor
     */
    public void setColorStyle(@ColorRes int backgroundColor, @ColorRes int textColor) {
        widgetEmptyErrorView.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        widgetEmptyErrorTv.setTextColor(ContextCompat.getColor(context, textColor));
    }

    /**
     * 点击回调
     */
    public interface OnSceenClickListener {
        /**
         * 点击事件
         */
        void onEevClick(boolean isBtnClick);
    }
}
