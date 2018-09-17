package com.chasen.italker.frags.main;

import android.view.View;

import com.chasen.common.app.Fragment;
import com.chasen.common.widget.GalleyView;
import com.chasen.italker.R;

import butterknife.BindView;

/**
 *
 */
public class ActiveFragment extends Fragment {
    @BindView(R.id.galley)
    GalleyView mGalley;

    public ActiveFragment() {

    }


    @Override
    protected int getContentLayId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mGalley.setup(getLoaderManager(), new GalleyView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
