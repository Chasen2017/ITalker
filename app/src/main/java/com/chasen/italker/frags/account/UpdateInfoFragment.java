package com.chasen.italker.frags.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.chasen.common.app.Application;
import com.chasen.common.app.Fragment;
import com.chasen.common.widget.PortraitView;
import com.chasen.italker.R;
import com.chasen.italker.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 更新信息的Fragment
 */
public class UpdateInfoFragment extends Fragment {

    GalleryFragment fragment;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
      new GalleryFragment()
                    .setListener(new GalleryFragment.OnSelectedListener() {
                        @Override
                        public void onSelectedImage(String path) {
                            UCrop.Options options = new UCrop.Options();
                            // 设置图片处理的格式
                            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                            // 设置压缩后的图片精度
                            options.setCompressionQuality(96);

                            File dPath = Application.getPortraitTemFile();

                            UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                            .withAspectRatio(1, 1)
                            .withMaxResultSize(520, 520)
                            .withOptions(options)
                            .start(getActivity());

                        }
                    })
              .show(getChildFragmentManager(), GalleryFragment.class.getSimpleName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (requestCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);

        }
    }

    private void loadPortrait(Uri uri) {
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }

}
