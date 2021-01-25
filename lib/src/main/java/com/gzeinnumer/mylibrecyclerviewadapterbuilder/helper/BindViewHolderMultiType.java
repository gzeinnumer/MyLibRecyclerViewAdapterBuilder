package com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper;

import android.view.View;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.model.TypeViewItem;

public interface BindViewHolderMultiType<T> {
    TypeViewItem getItemViewType(int position);

    void bind(View holder, T data, int position, int viewType);
}
