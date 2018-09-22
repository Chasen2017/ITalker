package com.chasen.italker.actvities;

import android.content.Context;
import android.content.Intent;

import com.chasen.common.app.Activity;
import com.chasen.common.app.Fragment;
import com.chasen.italker.R;
import com.chasen.italker.frags.account.UpdateInfoFragment;

public class AccountActivity extends Activity {

    private Fragment mCurFragment;

    /**
     * 账户Activity显示的入口
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurFragment = new UpdateInfoFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode, resultCode, data);
    }
}
