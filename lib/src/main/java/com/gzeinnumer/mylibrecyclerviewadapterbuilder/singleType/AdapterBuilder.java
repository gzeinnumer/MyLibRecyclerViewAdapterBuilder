package com.gzeinnumer.mylibrecyclerviewadapterbuilder.singleType;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolder;

import java.util.List;

public class AdapterBuilder<T> {
    private final AdapterCreator<T> adapterCreator;

    public AdapterBuilder(int rvItem) {
        this.adapterCreator = new AdapterCreator<T>(rvItem);
    }

    public AdapterBuilder<T> setCustomNoItem(int emptyViewContent) {
        adapterCreator.setEmptyLayout(emptyViewContent);
        return this;
    }

    public AdapterBuilder<T> setDivider(int divider) {
        adapterCreator.setDivider(divider);
        adapterCreator.setEnableDiffUtils(false);
        return this;
    }

    public AdapterBuilder<T> setAnimation(int animation) {
        adapterCreator.setAnimation(animation);
        return this;
    }

    public AdapterBuilder<T> setList(List<T> list) {
        adapterCreator.setList(list);
        return this;
    }

    public AdapterCreator<T> onBind(BindViewHolder<T> bindViewHolder) {
        adapterCreator.setBindViewHolder(bindViewHolder);
        return adapterCreator;
    }
}
