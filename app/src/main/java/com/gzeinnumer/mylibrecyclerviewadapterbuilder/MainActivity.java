package com.gzeinnumer.mylibrecyclerviewadapterbuilder;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.ActivityMainBinding;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.RvItemBinding;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.databinding.RvItemGenapBinding;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolderMultiType;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.FilterCallBack;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.model.TypeViewItem;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.multiType.AdapterBuilderMultiType;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.multiType.AdapterCreatorMultiType;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.singleType.AdapterBuilder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.singleType.AdapterCreator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        typeSingle();
        typeMulti();
    }

    private void typeSingle() {
        List<MyModel> list = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            list.add(new MyModel(i, "Data Ke " + (i + 1)));
        }
        AdapterCreator<MyModel> adapter = new AdapterBuilder<MyModel>(R.layout.rv_item)
                .setCustomNoItem(R.layout.custom_empty_item)
                .setDivider(R.layout.custom_divider)
                .setAnimation(R.anim.anim_two)
                .setList(list)
                .onBind(new BindViewHolder<MyModel>() {
                    @Override
                    public void bind(View holder, MyModel data, int position) {
                        RvItemBinding bindingItem = RvItemBinding.bind(holder);
                        bindingItem.btn.setText(data.getId() + "_" + data.getName());
                        bindingItem.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "tekan " + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .onFilter(new FilterCallBack<MyModel>() {
                    @Override
                    public List<MyModel> performFiltering(CharSequence constraint, List<MyModel> listFilter) {
                        List<MyModel> fildteredList = new ArrayList<>();
                        if (constraint != null || constraint.length() != 0) {
                            String filterPattern = constraint.toString().toLowerCase().trim();
                            for (MyModel item : listFilter) {
                                if (String.valueOf(item.getId()).toLowerCase().contains(filterPattern)) {
                                    fildteredList.add(item);
                                }
                            }
                        }
                        return fildteredList;
                    }
                });
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rv.hasFixedSize();
        binding.rv.setAdapter(adapter);

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                for (int i = 10; i < 100; i++) {
                    list.add(new MyModel(i, "Data Ke " + (i + 1)));
                }
                adapter.setList(list);
            }
        }.start();

        binding.ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }

    private void typeMulti() {
        List<MyModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyModel(i, "Data Ke " + (i + 1)));
        }
        AdapterCreatorMultiType<MyModel> adapter = new AdapterBuilderMultiType<MyModel>()
                .setCustomNoItem(R.layout.custom_empty_item)
                .setDivider(R.layout.custom_divider)
//                .setAnimation(R.anim.anim_two)
                .setList(list)
                .onBind(new BindViewHolderMultiType<MyModel>() {

                    private final int TYPE_GENAP = 1;
                    private final int TYPE_GANJIL = 0;

                    @Override
                    public TypeViewItem getItemViewType(int position) {
                        if (position % 2 == 0)
                            return new TypeViewItem(TYPE_GENAP, R.layout.rv_item_genap);
                        else
                            return new TypeViewItem(TYPE_GANJIL, R.layout.rv_item);
                    }

                    @Override
                    public void bind(View holder, MyModel data, int position, int viewType) {
                        if (viewType == TYPE_GENAP) {
                            RvItemGenapBinding bindingItem = RvItemGenapBinding.bind(holder);
                            bindingItem.btn.setText(data.getId() + "_" + data.getName() + "_Genap");
                            bindingItem.btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "tekan " + position, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (viewType == TYPE_GANJIL) {
                            RvItemBinding bindingItem = RvItemBinding.bind(holder);
                            bindingItem.btn.setText(data.getId() + "_" + data.getName() + "_Ganjil");
                            bindingItem.btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "tekan " + position, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                })
                .onFilter(new FilterCallBack<MyModel>() {
                    @Override
                    public List<MyModel> performFiltering(CharSequence constraint, List<MyModel> listFilter) {
                        List<MyModel> fildteredList = new ArrayList<>();
                        if (constraint != null || constraint.length() != 0) {
                            String filterPattern = constraint.toString().toLowerCase().trim();
                            for (MyModel item : listFilter) {
                                if (String.valueOf(item.getId()).toLowerCase().contains(filterPattern)) {
                                    fildteredList.add(item);
                                }
                            }
                        }
                        return fildteredList;
                    }
                });
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rv.hasFixedSize();
        binding.rv.setAdapter(adapter);

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                for (int i = 10; i < 100; i++) {
                    list.add(new MyModel(i, "Data Ke " + (i + 1)));
                }
                adapter.setList(list);
            }
        }.start();

        binding.ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }
}