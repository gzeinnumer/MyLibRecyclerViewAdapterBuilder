package com.gzeinnumer.mylibrecyclerviewadapterbuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.DefaultItemRvBinding;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AdapterCreator<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> list;

    private int emptyLayout = -1;
    private int animation = -1;
    private static int rvItemAdapter = -1;

    private BindViewHolder bindViewHolder;

    public AdapterCreator(int rvItem) {
        this.list = new ArrayList<>();
        rvItemAdapter = rvItem;
    }

    public void setEmptyLayout(int emptyLayout) {
        this.emptyLayout = emptyLayout;
    }
    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public void setBindViewHolder(BindViewHolder bindViewHolder) {
        this.bindViewHolder = bindViewHolder;
    }

    private static final int TYPE_NO_ITEM_VIEW = 2;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_EMPTY = 0;

    @Override
    public int getItemViewType(int position) {
        if (rvItemAdapter == -1) {
            return TYPE_NO_ITEM_VIEW;
        } else if (list.size() == 0){
            return TYPE_EMPTY;
        } else {
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(emptyLayout == -1 ? R.layout.default_empty_item : emptyLayout, parent, false));
        } else if (viewType == TYPE_NO_ITEM_VIEW){
            return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(R.layout.default_no_item_view, parent, false));
        } else {
            return new MyHolder(DefaultItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (animation!=-1){
            holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), animation));
        }
        if (list.size() > 0 && rvItemAdapter != -1) {
            try{
                ViewStub stub = ((MyHolder) holder).itemView.findViewById(R.id.layout_stub);
                stub.setLayoutResource(rvItemAdapter);
                View inflated = stub.inflate();
                bindViewHolder.bind(inflated, position);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(@NonNull DefaultItemRvBinding itemView) {
            super(itemView.getRoot());
        }
    }

    public static class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public ViewHolderEmpty(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }

    public void setList(List<T> d){
        if (this.list.size()==0){
            MyDiffUtilsCallBack diffUtilsCallBack = new MyDiffUtilsCallBack(d, list);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilsCallBack);

            list.addAll(d);
            diffResult.dispatchUpdatesTo(this);
        } else {
            MyDiffUtilsCallBack<T> diffUtilsCallBack = new MyDiffUtilsCallBack<T>(list, d);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilsCallBack);

            list.clear();
            list.addAll(d);
            diffResult.dispatchUpdatesTo(this);
        }
    }

}
