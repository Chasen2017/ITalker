package com.chasen.italker.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.chasen.common.tools.UiTool;
import com.chasen.common.widget.GalleryView;
import com.chasen.italker.R;

import net.qiujuer.genius.ui.Ui;

/**
 *
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {

    private GalleryView mGallery;
    private OnSelectedListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TransStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGallery = root.findViewById(R.id.galleryView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGallery.setup(getLoaderManager(), this);
    }


    @Override
    public void onSelectedCountChanged(int count) {
        // 如果选中了一张图片
        if (count > 0) {
            dismiss();
            if (mListener != null) {
                String[] paths = mGallery.getSelectedPath();
                mListener.onSelectedImage(paths[0]);
                mListener = null;
            }
        }
    }

    /**
     * 设置时间监听，并返回自己
     *
     * @param listener OnSelectedListener
     * @return GalleryFragment
     */
    public GalleryFragment setListener(OnSelectedListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * 选中图片的监听器
     */
    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }

    private static class TransStatusBottomSheetDialog extends BottomSheetDialog {

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Window window = getWindow();
            if (window == null)
                return;

            int screenHeight = UiTool.getScreenHeiht(getOwnerActivity());
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);

        }
    }

}
