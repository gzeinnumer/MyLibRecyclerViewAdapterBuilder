package com.gzeinnumer.mylibrecyclerviewadapterbuilder.base;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.DefaultItemRvBinding;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolderMultiType;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.FilterCallBack;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.MyDiffUtilsCallBack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public abstract class BaseBuilderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    protected static final int TYPE_EMPTY = 9999;
    protected static final int TYPE_NO_ITEM_VIEW = 9997;
    protected static final int TYPE_NORMAL = 9998;
    protected List<T> list;
    protected List<T> listFilter;
    protected List<T> listReal;
    protected int emptyLayout = -1;
    protected int divider = -1;
    protected int animation = -1;
    protected boolean diffutils = true;
    protected FilterCallBack<T> filterCallBack;
    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint.length() > 0) {
                results.values = filterCallBack.performFiltering(constraint, listFilter);
            } else {
                results.values = listReal;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public BaseBuilderAdapter() {
        this.list = new ArrayList<>();
        this.listFilter = new ArrayList<>();
        this.listReal = new ArrayList<>();
    }

    public void setEmptyLayout(int emptyLayout) {
        this.emptyLayout = emptyLayout;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public void setEnableDiffUtils(boolean enable) {
        this.diffutils = enable;
    }

    public void setList(List<T> d) {
        if (diffutils) {
            if (this.list.size() == 0) {
                MyDiffUtilsCallBack<T> diffUtilsCallBack = new MyDiffUtilsCallBack<T>(d, list);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilsCallBack);

                list.addAll(d);
                listFilter.addAll(d);
                listReal.addAll(d);
                diffResult.dispatchUpdatesTo(this);
            } else {
                MyDiffUtilsCallBack<T> diffUtilsCallBack = new MyDiffUtilsCallBack<T>(list, d);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilsCallBack);

                list.clear();
                listFilter.clear();
                listReal.clear();
                list.addAll(d);
                listFilter.addAll(d);
                listReal.addAll(d);
                diffResult.dispatchUpdatesTo(this);
            }
        } else {
            this.list = new ArrayList<>(d);
            this.listFilter = new ArrayList<>(d);
            this.listReal = new ArrayList<>(d);
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }

    public static class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public ViewHolderEmpty(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class MyHolder<T> extends RecyclerView.ViewHolder {
        private final DefaultItemRvBinding itemRvBinding;
        private final View view;
        private final int viewType;

        public MyHolder(@NonNull DefaultItemRvBinding itemView, int viewType) {
            super(itemView.getRoot());
            this.view = itemView.getRoot();
            this.itemRvBinding = itemView;
            this.viewType = viewType;
        }

        public void bind(T data, BindViewHolder<T> bindViewHolder, int size, int divider) {
            if (divider != -1) {
                if (getAdapterPosition() == size - 1) {
                    itemRvBinding.layoutDivider.setVisibility(View.GONE);
                } else {
                    itemRvBinding.layoutDivider.setVisibility(View.VISIBLE);
                }
            }
            bindViewHolder.bind(view, data, getAdapterPosition());
        }

        public void bind(T data, BindViewHolderMultiType<T> bindViewHolderMultiType, int size, int divider) {
            if (divider != -1) {
                if (getAdapterPosition() == size - 1) {
                    itemRvBinding.layoutDivider.setVisibility(View.GONE);
                } else {
                    itemRvBinding.layoutDivider.setVisibility(View.VISIBLE);
                }
            }
            bindViewHolderMultiType.bind(view, data, getAdapterPosition(), viewType);
        }
    }
}