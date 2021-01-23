package com.gzeinnumer.mylibrecyclerviewadapterbuilder;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyDiffUtilsCallBack<T> extends DiffUtil.Callback {

    private final List<T> oldList;
    private final List<T> newList;

    public MyDiffUtilsCallBack(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
