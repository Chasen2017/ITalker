package com.chasen.italker;

import android.widget.TextView;

import com.chasen.common.app.Activity;

import butterknife.BindView;

public class MainActivity extends Activity {

    @BindView(R.id.txt_test)
    TextView mTxt;


    @Override
    protected int getContentLayId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTxt.setText("hello imooc");
    }
}
