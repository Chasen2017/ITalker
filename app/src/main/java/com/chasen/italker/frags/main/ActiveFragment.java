package com.chasen.italker.frags.main;

import android.view.View;

import com.chasen.common.app.Fragment;
import com.chasen.common.widget.GalleryView;
import com.chasen.italker.R;

import butterknife.BindView;

/**
 *
 */
public class ActiveFragment extends Fragment {
    @BindView(R.id.galley)
    GalleryView mGalley;

    public ActiveFragment() {

    }


    @Override
    protected int getContentLayId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mGalley.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
