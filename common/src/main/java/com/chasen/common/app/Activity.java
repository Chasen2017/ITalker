package com.chasen.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by chasen on 18-6-5.
 *
 * 对Activity进行一层封装
 */

public abstract class Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            setContentView(getContentLayId());
            initWidget();
            initData();
        } else {
            finish();
        }



    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {
    }

    /**
     * 获得布局id，子类必须覆写
     */
    protected abstract int getContentLayId();

    /**
     * 初始化传递过来的参数
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 初始化控件
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 遍历当前Activity中的Fragment，判断是否拦截了back事件
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof com.chasen.common.app.Fragment) {
                if (((com.chasen.common.app.Fragment) fragment).onBackPressed()) {
                    return;
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
