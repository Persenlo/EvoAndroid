package com.qxy.evoandroid.utils;

import android.view.View;

import java.util.Calendar;

/**
 * 点击监听器（仅允许单次点击）
 * @author Persenlo
 */
public abstract class SingleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;//这里设置不能超过多长时间

    private long lastClickTime = 0;

    protected abstract void onSignalClick(View v) ;

    @Override

    public void onClick(View v) {

        long currentTime = Calendar.getInstance().getTimeInMillis();

        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {

            lastClickTime = currentTime;

            onSignalClick(v);

        }

    }
}
