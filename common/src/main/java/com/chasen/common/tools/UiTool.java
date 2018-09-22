package com.chasen.common.tools;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Window;


/**
 * Created by chasen on 18-9-22.
 *
 * 用于计算屏幕UI宽高
 */

public class UiTool {

    private static int STATUS_BAR_HEIGHT = -1;

    /**
     * 得到状态栏的高度
     * @param activity Activity
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && STATUS_BAR_HEIGHT == -1) {
            try {
                // 尝试获取status_bar_height这个属性的Id对应的资源int值
                final Resources res = activity.getResources();
                int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId <= 0) {
                    Class<?> clazz = Class.forName("com.andoird.interpolator.R$dimen");
                    Object object = clazz.newInstance();
                    resourceId = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                }

                // 如果拿到了就直接调用获取值
                if (resourceId > 0) {
                    STATUS_BAR_HEIGHT = res.getDimensionPixelSize(resourceId);
                }

                // 如果还是未拿到
                if (resourceId <= 0) {
                    // 通过window获取
                    Rect rect = new Rect();
                    Window window = activity.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                    STATUS_BAR_HEIGHT = rect.top;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return STATUS_BAR_HEIGHT;
    }

    /**
     * 得到屏幕的宽度
     * @param activity Activity
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 得到屏幕的高度
     * @param activity Activity
     * @return 屏幕的高度
     */
    public static int getScreenHeiht(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


}
