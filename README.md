<p align="center">
  <img src="https://www.teahub.io/photos/full/1-10331_cute-baby-wallpapers-photos-high-resolution-for-computer.jpg"/>
</p>

<h1 align="center">
    MyLibRecyclerViewAdapterBuilder
</h1>

<p align="center">
    <a><img src="https://img.shields.io/badge/Version-1.1.0-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Koltin-Suport-green?logo=kotlin&style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <p>Say Good Bye To <b>Adapter</b> .</p>
</p>

---
## Download
Add maven `jitpack.io` and `dependencies` in `build.gradle (Project)` :
```gradle
// build.gradle project
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

// build.gradle app/module
dependencies {
  ...
  implementation 'com.github.gzeinnumer:MyLibRecyclerViewAdapterBuilder:version'
}

// enable view binding
android {

    ...

    //Android Studio Version Until 4
    viewBinding {
        enabled = true
    }

    //Android Studio Version 4 -> gradle version 6.1.1 -> android gradle plugin version 4.0.0
    buildFeatures{
        viewBinding = true
    }
}
```

Read More For Viewbinding [Java](https://github.com/gzeinnumer/ViewBindingExample) & [Kotlin](https://github.com/gzeinnumer/ViewBindingExampleKT)

## Feature List
- [x] Diffutil
- [x] Adapter Builder
- [x] Empty List State
- [x] Animation

## Tech stack and 3rd library
- [View Binding](https://developer.android.com/topic/libraries/view-binding?hl=id)
- [DiffUtil](https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil)

---
## USE

### Make Builder
> Java
```java
List<MyModel> list = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    list.add(new MyModel(i,"Data Ke "+ (i + 1)));
}
AdapterCreator<MyModel> adapter = new AdapterBuilder<MyModel>(R.layout.rv_item)
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
        });

binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
binding.rv.hasFixedSize();
binding.rv.setAdapter(adapter);

//after 5 second, new data will appear
new CountDownTimer(5000, 1000) {
    public void onTick(long millisUntilFinished) {
    }

    public void onFinish() {
        for (int i = 10; i < 100; i++) {
            list.add(new MyModel(i,"Data Ke "+ (i + 1)));
        }
        adapter.setList(list);
    }
}.start();
```

> Kotlin
```kotlin
val list: MutableList<MyModel> = ArrayList()
for (i in 0..9) {
    list.add(MyModel(i, "Data Ke " + (i + 1)))
}

val adapter = AdapterBuilder<MyModel>(R.layout.rv_item)
        .setList(list)
        .onBind { holder, data,  position ->
            //rv_item = RvItemBinding
            val b = RvItemBinding.bind(holder)
            b.btn.text = data.id + "_" + data.name
            b.btn.setOnClickListener { Toast.makeText(this@MainActivity, "tekan $position", Toast.LENGTH_SHORT).show() }
        }

binding.rv.layoutManager = LinearLayoutManager(applicationContext)
binding.rv.hasFixedSize()
binding.rv.adapter = adapter

//after 5 second, new data will appear
object : CountDownTimer(5000, 1000) {
    override fun onTick(millisUntilFinished: Long) {}
    override fun onFinish() {
        for (i in 10..100) {
            list.add(MyModel(i, "Data Ke " + (i + 1)))
        }
        adapter.setList(list)
    }
}.start()
```

### Enable Filter

Use `onFilter` after `onBind`.
> Java
```java
AdapterCreator<MyModel> adapter = new AdapterBuilder<MyModel>(R.layout.rv_item)
    .onBind( ... )
    .onFilter(new FilterCallBack<MyModel>() {
        @Override
        public List<MyModel> performFiltering(CharSequence constraint, List<MyModel> listFilter) {
            List<MyModel> fildteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                Collections.sort(listFilter, new Comparator<MyModel>() {
                    @Override
                    public int compare(MyModel o1, MyModel o2) {
                        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                    }
                });
                fildteredList.addAll(listFilter);
            } else {
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
```
> Kotlin
```kotlin
val adapter: AdapterCreator<MyModel> = AdapterBuilder<MyModel>(R.layout.rv_item)
    .onBind { ... }
    .onFilter { constraint, listFilter ->
        val fildteredList: MutableList<MyModel> = ArrayList()

        if (constraint == null || constraint.isEmpty()) {
            listFilter.sortWith(Comparator { o1, o2 ->
                o1.name.toLowerCase().compareTo(o2.name.toLowerCase())
            })
            fildteredList.addAll(listFilter)
        } else {
            val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
            for (item in listFilter) {
                if (item.id.toString().toLowerCase().contains(filterPattern)) {
                    fildteredList.add(item)
                }
            }
        }
        fildteredList
    }
```
here is sample code in `AdapterRv extends RecyclerView.Adapter<>` that you can use [RecyclerViewSearchMultiItem](https://github.com/gzeinnumer/RecyclerViewSearchMultiItem)
<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/preview/example5.jpg" width="300"/>
</p>
---
Preview :

Full Code
[MainActivity.java](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/app/src/main/java/com/gzeinnumer/mylibrecyclerviewadapterbuilder/MainActivity.java)
 & [MyModel.java](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/app/src/main/java/com/gzeinnumer/mylibrecyclerviewadapterbuilder/MyModel.java)
 & [activity_main.xml](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/app/src/main/res/layout/activity_main.xml)
 & [rv_item.xml](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/app/src/main/res/layout/rv_item.xml)

|<img src="https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/preview/example1.jpg" width="300"/>|<img src="https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/preview/example2.jpg" width="300"/>|
|---|---|
|If `list size = 0` | Your Custom Item View |

---

## Customize

- You can customize Empty item Message or `list size = 0` with
> Java
```java
new AdapterBuilder<MyModel>(R.layout.rv_item)
    .setCustomNoItem(R.layout.custom_empty_item)
```
>Kotlin
```kotlin
AdapterBuilder<MyModel>(R.layout.rv_item)
    .setCustomNoItem(R.layout.custom_empty_item)
```
Sample code [custom_empty_item.xml](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/app/src/main/res/layout/custom_empty_item.xml)
<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/preview/example3.jpg" width="300"/>
</p>

#
- You can customize animation in recycler view with
> Java
```java
new AdapterBuilder<MyModel>(R.layout.rv_item)
    .setAnimation(R.anim.anim_two)
```
>Kotlin
```kotlin
AdapterBuilder<MyModel>(R.layout.rv_item)
    .setAnimation(R.anim.anim_two)
```
here is animation that you can use [RecyclerViewAnimation](https://github.com/gzeinnumer/RecyclerViewAnimation)

#
- Custom Divider
> Java
```java
AdapterCreator<MyModel> adapter = new AdapterBuilder<MyModel>(R.layout.rv_item)
    .setDivider(R.layout.custom_divider)
```
>Kotlin
```kotlin
val adapter: AdapterCreator<MyModel> = BuildAdapter<MyModel>(R.layout.rv_item)
    .setDivider(R.layout.custom_divider)
```
Sample code [custom_divider.xml](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/app/src/main/res/layout/custom_divider.xml)
<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/dev-1/preview/example4.jpg" width="300"/>
</p>
---

Sample APP, just clone it [Java](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilderExample) & [Kotlin](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilderExampleKT)

---

### Version
- **1.0.1**
  - First Release
- **1.1.0**
  - Add Filter Function

---

### Contribution
You can sent your constibution to `branche` `open-pull`.

---

```
Copyright 2021 M. Fadli Zein
```
