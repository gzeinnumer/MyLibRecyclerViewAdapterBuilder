package com.gzeinnumer.mylibrecyclerviewadapterbuilder;

import java.util.List;

public class AdapterBuilder<T> {
    AdapterCreator<T> adapterCreator;

    public AdapterBuilder(int rvItem) {
        this.adapterCreator = new AdapterCreator<T>(rvItem);
    }

    public AdapterBuilder<T> setCustomNoItem(int emptyViewContent) {
        adapterCreator.setEmptyLayout(emptyViewContent);
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

    public AdapterBuilder<T> enableDiffUtils(boolean enable) {
        adapterCreator.setEnableDiffUtils(enable);
        return this;
    }

    public AdapterCreator<T> onBind(BindViewHolder bindViewHolder) {
        adapterCreator.setBindViewHolder(bindViewHolder);
        return adapterCreator;
    }
}
