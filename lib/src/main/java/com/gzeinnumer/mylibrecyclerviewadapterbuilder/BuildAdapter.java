package com.gzeinnumer.mylibrecyclerviewadapterbuilder;

import java.util.List;

public class BuildAdapter<T> {
    AdapterCreator<T> adapterCreator;

    public BuildAdapter(int rvItem) {
        this.adapterCreator = new AdapterCreator<T>(rvItem);
    }

    public BuildAdapter<T> setCustomNoItem(int emptyViewContent){
        adapterCreator.setEmptyLayout(emptyViewContent);
        return this;

    }

    public BuildAdapter<T> setAnimation(int animation){
        adapterCreator.setAnimation(animation);
        return this;
    }

    public BuildAdapter<T> setList(List<T> list) {
        adapterCreator.setList(list);
        return this;
    }

    public AdapterCreator<T> onBind(BindViewHolder bindViewHolder) {
        adapterCreator.setBindViewHolder(bindViewHolder);
        return adapterCreator;
    }
}
