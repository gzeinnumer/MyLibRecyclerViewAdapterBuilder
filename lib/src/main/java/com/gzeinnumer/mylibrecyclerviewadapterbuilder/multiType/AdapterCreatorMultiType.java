package com.gzeinnumer.mylibrecyclerviewadapterbuilder.multiType;

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
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolderMultiType;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.FilterCallBack;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.model.TypeViewItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AdapterCreatorMultiType<T> extends BaseBuilderAdapter<T> {

    public static final String TAG = "Adapter_Creator";
    private List<TypeViewItem> typeViewItem;
    private BindViewHolderMultiType<T> bindViewHolderMultiType;

    public AdapterCreatorMultiType() {
        super();
        this.typeViewItem = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0) {
            return TYPE_EMPTY;
        } else {
            typeViewItem.add(bindViewHolderMultiType.getItemViewType(position));
            return bindViewHolderMultiType.getItemViewType(position).getType();
        }
    }

    public void setBindViewHolderMultiType(BindViewHolderMultiType<T> bindViewHolderMultiType) {
        this.bindViewHolderMultiType = bindViewHolderMultiType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(emptyLayout == -1 ? R.layout.default_empty_item : emptyLayout, parent, false));
        } else {
            DefaultItemRvBinding defaultItemRvBinding = DefaultItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            for (int i = 0; i < typeViewItem.size(); i++) {
                if (viewType == typeViewItem.get(i).getType()) {
                    ViewStub stub = defaultItemRvBinding.layoutStub;
                    stub.setLayoutResource(typeViewItem.get(i).getLayout());
                    View inflated = stub.inflate();
                    if (divider != -1) {
                        ViewStub stubDiv = defaultItemRvBinding.layoutDivider;
                        stubDiv.setLayoutResource(divider);
                        View inflatedDiv = stubDiv.inflate();
                    }
                    break;
                }
            }

            return new MyHolder<T>(defaultItemRvBinding, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (animation != -1) {
            holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), animation));
        }
        if (holder.getItemViewType() != TYPE_EMPTY) {
            if (list.size() > 0) {
                ((MyHolder<T>) holder).bind(list.get(position), bindViewHolderMultiType, list.size(), divider);
            }
        }
    }

    public AdapterCreatorMultiType<T> onFilter(FilterCallBack<T> filterCallBack) {
        this.filterCallBack = filterCallBack;
        return this;
    }
}
