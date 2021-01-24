package com.gzeinnumer.mylibrecyclerviewadapterbuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.DefaultItemRvBinding;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.FilterCallBack;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.MyDiffUtilsCallBack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AdapterCreator<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    public static final String TAG = "Adapter_Creator";
    private List<T> list;
    private List<T> listFilter;
    private List<T> listReal;

    private int emptyLayout = -1;
    private int divider = -1;
    private int animation = -1;
    private static int rvItemAdapter = -1;
    private boolean diffutils = true;
    private BindViewHolder<T> bindViewHolder;
    private FilterCallBack<T> filterCallBack;

    public void setEmptyLayout(int emptyLayout) {
        this.emptyLayout = emptyLayout;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint.length() > 0) {
                List<T> data = filterCallBack.performFiltering(constraint, listFilter);
                results.values = data;
                return results;
            } else {
                results.values = listReal;
                return results;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public AdapterCreator(int rvItem) {
        this.list = new ArrayList<>();
        this.listFilter = new ArrayList<>();
        this.listReal = new ArrayList<>();
        this.rvItemAdapter = rvItem;
    }

    private static final int TYPE_NO_ITEM_VIEW = 2;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_EMPTY = 0;

    @Override
    public int getItemViewType(int position) {
        if (rvItemAdapter == -1) {
            return TYPE_NO_ITEM_VIEW;
        } else if (list.size() == 0) {
            return TYPE_EMPTY;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public void setBindViewHolder(BindViewHolder<T> bindViewHolder) {
        this.bindViewHolder = bindViewHolder;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(emptyLayout == -1 ? R.layout.default_empty_item : emptyLayout, parent, false));
        } else if (viewType == TYPE_NO_ITEM_VIEW) {
            return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(R.layout.default_no_item_view, parent, false));
        } else {
            DefaultItemRvBinding defaultItemRvBinding = DefaultItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            ViewStub stub = defaultItemRvBinding.layoutStub;
            stub.setLayoutResource(rvItemAdapter);
            View inflated = stub.inflate();
            if (divider != -1) {
                ViewStub stubDiv = defaultItemRvBinding.layoutDivider;
                stubDiv.setLayoutResource(divider);
                View inflatedDiv = stubDiv.inflate();
            }
            return new MyHolder<T>(defaultItemRvBinding);
        }
    }

    public void setEnableDiffUtils(boolean enable) {
        this.diffutils = enable;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (animation != -1) {
            holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), animation));
        }
        if (holder.getItemViewType() == TYPE_NORMAL) {
            if (list.size() > 0 && rvItemAdapter != -1) {
                ((MyHolder<T>) holder).bind(list.get(position), bindViewHolder, list.size(), divider);
            }
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

    public AdapterCreator<T> onFilter(FilterCallBack<T> filterCallBack) {
        this.filterCallBack = filterCallBack;
        return this;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public static class MyHolder<T> extends RecyclerView.ViewHolder {
        DefaultItemRvBinding itemRvBinding;
        View view;

        public MyHolder(@NonNull DefaultItemRvBinding itemView) {
            super(itemView.getRoot());
            view = itemView.getRoot();
            itemRvBinding = itemView;
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
    }
}
