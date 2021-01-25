package com.gzeinnumer.mylibrecyclerviewadapterbuilder.singleType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.R;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.base.BaseBuilderAdapter;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.DefaultItemRvBinding;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.FilterCallBack;

@SuppressWarnings("ALL")
public class AdapterCreator<T> extends BaseBuilderAdapter<T> {

    public static final String TAG = "Adapter_Creator";
    private static int rvItemAdapter = -1;
    private BindViewHolder<T> bindViewHolder;

    public AdapterCreator(int rvItem) {
        super();
        this.rvItemAdapter = rvItem;
    }

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
            return new MyHolder<T>(defaultItemRvBinding, viewType);
        }
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

    public AdapterCreator<T> onFilter(FilterCallBack<T> filterCallBack) {
        this.filterCallBack = filterCallBack;
        return this;
    }
}
