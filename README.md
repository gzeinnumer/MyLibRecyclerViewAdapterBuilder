<p align="center">
  <img src="https://www.teahub.io/photos/full/1-10331_cute-baby-wallpapers-photos-high-resolution-for-computer.jpg"/>
</p>

<h1 align="center">
    MyLibRecyclerViewAdapterBuilder
</h1>

<p align="center">
    <a><img src="https://img.shields.io/badge/Version-1.0.0-brightgreen.svg?style=flat"></a>
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
```

## Feature List
- [x] [View No Item](#make-class-table)
- [x] [Animation](#make-class-table)

## Tech stack and 3rd library
- [View Binding](https://developer.android.com/topic/libraries/view-binding?hl=id)

---
## USE

> Java
```java
List<MyModel> list = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    list.add(new MyModel(i,"Data Ke "+ (i + 1)));
}
AdapterCreator<MyModel> adapter = new BuildAdapter<MyModel>(R.layout.rv_item)
        .setList(list)
        .onBind(new BindViewHolder() {
            @Override
            public void bind(View holder, int position) {
                RvItemBinding b = RvItemBinding.bind(holder);
                b.btn.setText(list.get(position).id+"_"+list.get(position).name);
                b.btn.setOnClickListener(new View.OnClickListener() {
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
        for (int i = 0; i < 10; i++) {
            list.add(new MyModel(i,"Data Ke "+ (i + 1)));
        }
        adapter.setList(list);
    }
}.start();
```

> Kotlin
```java
val list: MutableList<MyModel> = ArrayList()
for (i in 0..9) {
    list.add(MyModel(i, "Data Ke " + (i + 1)))
}

val adapter: AdapterCreator<MyModel> = BuildAdapter<MyModel>(R.layout.rv_item)
        .setList(list)
        .onBind { holder, position ->
            val b = RvItemBinding.bind(holder)
            b.btn.text = list[position].id.toString() + "_" + list[position].name
            b.btn.setOnClickListener { Toast.makeText(this@MainActivity, "tekan $position", Toast.LENGTH_SHORT).show() }
        }

binding.rv.layoutManager = LinearLayoutManager(applicationContext)
binding.rv.hasFixedSize()
binding.rv.adapter = adapter

//after 5 second, new data will appear
object : CountDownTimer(5000, 1000) {
    override fun onTick(millisUntilFinished: Long) {}
    override fun onFinish() {

        adapter.setList(list)
    }
}.start()
```
Preview :
Full Code
[MainActivity.java](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/master/app/src/main/java/com/gzeinnumer/mylibrecyclerviewadapterbuilder/MainActivity.java)
 & [MyModel.java](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/master/app/src/main/java/com/gzeinnumer/mylibrecyclerviewadapterbuilder/MyModel.java)
 & [activity_main.xml](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/master/app/src/main/res/layout/activity_main.xml)
 & [rv_item.xml](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/master/app/src/main/res/layout/rv_item.xml)

|![](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/master/preview/example1.jpg)|![](https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder/blob/master/preview/example2.jpg)|
|---|---|
|If `list` size = 0| Your Custom Item View |

---

Sample APP, just clone it [Java](https://github.com/gzeinnumer/MyLibSimpleSQLite) & [Kotlin](https://github.com/gzeinnumer/MyLibSimpleSQLitekt)

---

### Version
- **1.0.0**
  - First Release

---

### Contribution
You can sent your constibution to `branche` `open-pull`.

---

```
Copyright 2021 M. Fadli Zein
```
