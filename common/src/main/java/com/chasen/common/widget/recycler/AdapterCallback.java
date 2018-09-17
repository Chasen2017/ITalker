package com.chasen.common.widget.recycler;


/**
 * Created by chasen on 18-6-14.
 */

public interface AdapterCallback<Data> {

    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
