package com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper;

import android.view.View;

public interface BindViewHolder<T> {
    void bind(View holder, T data, int position);
}
