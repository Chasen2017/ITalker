package com.chasen.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chasen.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chasen on 18-6-14.
 * <p>
 * RecyclerAdapter的简单封装
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    /**
     * 构造函数
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 覆写默认的布局类型返回
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        root.setTag(R.id.tag_recycler_holder, holder);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        holder.unbinder = ButterKnife.bind(holder, root);
        holder.callback = this;
        return null;
    }

    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到holder上
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        Data data = mDataList.get(position);
        holder.bind(data);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一个数据，并通知更新
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并通知更新
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, mDataList.size());
        }
    }

    /**
     * 插入一堆数据，并通知更新
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, mDataList.size());
        }
    }

    /**
     * 替换为一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mDataList.addAll(dataList);
    }

    /**
     * 删除
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置设配器监听
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     */
    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * ViewHolder封装
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        protected Data mData;
        private Unbinder unbinder;
        private AdapterCallback<Data> callback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候的回调，必须复写
         */
        protected abstract void onBind(Data data);

        /**
         * Holder自己对自己更新的操作
         */
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }
    }
}
