
[![](https://jitpack.io/v/BaselHorany/FadedRecyclerView.svg)](https://jitpack.io/#BaselHorany/FadedRecyclerView)

# FadedRecyclerView
focus on visible items and others faded depeinding on their visibility percentage

Just like video playlist in facebook app
so possible names that explains it and also brings you here from google search:
 * recyclerview hides items like facebook videos
 * fade recyclerview items
 * Alan Walker recyclerview ?


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
  compile 'com.github.BaselHorany:FadedRecyclerView:1.0.0'
}
```

## Usage
Just like how you would use a reqular RecyclerView that is why there is no sample app attached

```java

/*options*/
recyclerview.setMaskColor(initColor);// default black
recyclerview.setIsAggressive(false);//default true. if items should be dimmed aggressively

//Listener
recyclerview.setVisibilityListener(new FadedRecyclerView.VisibilityListener() {
    public void onVisibleChanged(int position, int visibilityPercentage) {
      //ex: toast you are almost there! or deal again with that item 
    }
});

```


## Author
Basel Horany 
[http://baselhorany.com](http://baselhorany.com)

