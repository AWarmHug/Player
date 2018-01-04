package com.warm.playerlib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * 作者：warm
 * 时间：2017-12-23 14:07
 * 描述：
 */

public class DisplayUtils {


    public static void showBar(Context activity) {
        if (activity == null) {
            return;
        }
        showStateBar(activity);
        showActionBar(activity);
        showNavBar(activity);
    }

    public static void hideBar(Context activity) {
        if (activity == null) {
            return;
        }
        hideStateBar(activity);
        hideActionBar(activity);
        hideNavBar(activity);
    }

    private static void showStateBar(Context activity) {
        if (activity instanceof Activity) {
            Activity activity1 = (Activity) activity;
            activity1.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity1.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private static void hideStateBar(Context activity) {
        if (activity instanceof Activity) {
            Activity activity1 = (Activity) activity;
            activity1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
    }


    private static void showActionBar(Context activity) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        } else {
            Activity activity1 = (Activity) activity;
            android.app.ActionBar actionBar = activity1.getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }


    }

    private static void hideActionBar(Context activity) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        } else {
            Activity activity1 = (Activity) activity;
            android.app.ActionBar actionBar = activity1.getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }


    /**
     * 显示NavigationBar
     */
    private static void showNavBar(Context activity) {
        if (activity instanceof Activity) {
            ((Activity) activity).getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }

    /**
     * 隐藏NavigationBar
     */
    private static void hideNavBar(Context activity) {
        if (activity instanceof Activity) {

            int flag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //       设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
                flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } else {
                flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // hide nav
            }
            ((Activity) activity).getWindow().getDecorView().setSystemUiVisibility(flag);
        }
    }

}
