package com.chasen.common.app;

import android.os.SystemClock;

import java.io.File;

/**
 * Created by chasen on 18-9-22.
 */

public class Application extends android.app.Application {

    public static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    /**
     * 得到缓存文件的地址
     *
     * @return 缓存文件的地址
     */
    public static File getCacheDirFilel() {
        return sInstance.getCacheDir();
    }

    public static File getPortraitTemFile() {
        // 得到头像目录的缓存地址
        File dir = new File(getCacheDirFilel(), "portrait");
        // 创建所有的对应的文件夹
        dir.mkdirs();
        // 删除旧的缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }

        // 返回一个当前时间戳的目录文件地址
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();

    }

}
