package com.chasen.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chasen on 18-6-5.
 *
 * Fragment的封装
 */

public abstract class Fragment extends android.support.v4.app.Fragment{

    protected View mRoot;
    protected Unbinder mRootUnBinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRoot == null) {
            View root = inflater.inflate(getContentLayId(), container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化传递过来的参数
     */
    protected void initArgs(Bundle bundle){

    }

    /**
     * 得到布局Id，子类必须覆写
     * @return
     */
    protected abstract int getContentLayId();

    /**
     * 初始化控件
     */
    protected void initWidget(View root){
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 返回按键处理
     * @return 返回True代表Fragment已经处理，Activity不用处理，
     * 返回False代表交给Activity处理
     */
    public boolean onBackPressed() {
        return false;
    }

}
