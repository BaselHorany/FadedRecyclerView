
[![](https://jitpack.io/v/BaselHorany/FadedRecyclerView.svg)](https://jitpack.io/#BaselHorany/FadedRecyclerView)

# FadedRecyclerView
focus on visible items and others faded depeinding on their visibility percentage

Just like video playlist in facebook app
so possible names that explains it and also brings you here from google search:
 * night mode recyclerview
 * fade recyclerview items on scroll


<p align="center">
  <img src="https://github.com/BaselHorany/FadedRecyclerView/blob/master/showcase.gif?raw=true" />
</p>


## Setup
1- Add jitpack.io repositories to you project `build.gradle`
```groovy 
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
2- Add it as a dependency to your app `build.gradle`
```groovy
dependencies {
  compile 'com.github.BaselHorany:FadedRecyclerView:1.0.2'
}
```

## Usage
Just like how you would use a reqular RecyclerView that is why there is no sample app attached

```xml
<com.basel.FadedRecyclerView.FadedRecyclerView
    android:id="@+id/rv"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

```java

/*options*/ 
//if your design prefer to not show the item under TabLayout, Toolbar or whatever is above the recyclerview.
recyclerview.setPreventOverlapTop(true);
//default fade mode is foreground overlay try both and use what you like.
recyclerview.setFadeModeAlpha(true);
//default black
recyclerview.setMaskColor(initColor);
//default true. if items should be dimmed aggressively
recyclerview.setIsAggressive(false);

//Listener
recyclerview.setVisibilityListener(new FadedRecyclerView.VisibilityListener() {
    public void onVisibleChanged(int position, int visibilityPercentage) {
      //ex: toast you are almost there! or deal again with that item 
    }
});

```

## This lib uses AndroidX

## Author
Basel Horany 
[http://baselhorany.com](http://baselhorany.com)

