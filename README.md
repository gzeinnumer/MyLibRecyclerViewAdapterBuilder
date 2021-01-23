<p align="center">
  <img src="https://www.teahub.io/photos/full/1-10331_cute-baby-wallpapers-photos-high-resolution-for-computer.jpg"/>
</p>

<h1 align="center">
    MyLibRecyclerViewAdapterBuilder
</h1>

<p align="center">
    <a><img src="https://img.shields.io/badge/Version-1.0.3-brightgreen.svg?style=flat"></a>
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
- [x] [Make Class Table](#make-class-table)

## Tech stack and 3rd library
- [SQLite](https://developer.android.com/training/data-storage/sqlite?hl=id)

---
## USE

### Check File Database Exists On Root
```java
...
public class DBInstance extends SQLiteBuilder {

    ...

    public boolean isDBExistOnRoot(Context context){
        String DB_NAME = "MyLibSQLiteSimple.db";
        return isDatabaseExistOnRoot(context, DB_NAME);
    }
}
```

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
