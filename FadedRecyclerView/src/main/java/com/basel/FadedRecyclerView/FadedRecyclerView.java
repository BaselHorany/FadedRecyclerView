package com.basel.FadedRecyclerView;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FadedRecyclerView extends RecyclerView {

    public FadedRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FadedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FadedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private LayoutManager layoutManger;
    private boolean isVertical,isLinear,isAggressive,isFadeModeAlpha;
    private Rect rvGlobalRect,rvLocalRect,itemBoundsRect;
    private int red,green,blue;
    private VisibilityListener vListener;

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        setIsAggressive(true);
        setMaskColor(Color.parseColor("#000000"));
        rvGlobalRect = new Rect();
        rvLocalRect = new Rect();
        itemBoundsRect = new Rect();
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(layoutManger==null){
                    layoutManger = getLayoutManager();
                }
                getItems();
            }
        });
    }


    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        if(layout!=null){
            layoutManger = layout;
            isVertical = layoutManger.canScrollVertically();
            isLinear = layoutManger instanceof LinearLayoutManager;
        }
    }

    public void setIsAggressive(boolean isAggressive) {
        this.isAggressive = isAggressive;
    }

    public void setMaskColor(int color) {
        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);
    }

    private void getItems() {
        //should get bounds every time because of pinnable toolbar and tablayout if any
        getGlobalVisibleRect(rvGlobalRect);
        getLocalVisibleRect(rvLocalRect);
        int firstVisibleItem = 0;
        int lastVisibleItem = 0;
        if(isLinear){
            LinearLayoutManager linearLayoutManger = (LinearLayoutManager)layoutManger;
            firstVisibleItem =  linearLayoutManger.findFirstVisibleItemPosition();
            lastVisibleItem =  linearLayoutManger.findLastVisibleItemPosition();
        }else{
            GridLayoutManager gridLayoutManger = (GridLayoutManager)layoutManger;
            firstVisibleItem = gridLayoutManger.findFirstVisibleItemPosition();
            lastVisibleItem = gridLayoutManger.findLastVisibleItemPosition();
        }
        calculateVisibility(firstVisibleItem,lastVisibleItem);
    }

    private void calculateVisibility(int firstVisibleItem, int lastVisibleItem) {
        for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
            int visibleSize;
            int visibilty;
            View item = layoutManger.findViewByPosition(i);
            if (item == null) {
                continue;
            }
            item.getGlobalVisibleRect(itemBoundsRect);
            int itemSize = layoutManger.findViewByPosition(i).getHeight();
            if (!isVertical) {
                itemSize = layoutManger.findViewByPosition(i).getWidth();
            }
            if (itemBoundsRect.bottom >= rvLocalRect.bottom) {
                visibleSize = rvGlobalRect.bottom - itemBoundsRect.top;
                if (!isVertical) {
                    visibleSize = rvGlobalRect.right - itemBoundsRect.left;
                }
            } else {
                visibleSize = itemBoundsRect.bottom - rvGlobalRect.top;
                if (!isVertical) {
                    visibleSize = itemBoundsRect.right - rvGlobalRect.left;
                }
            }
            visibilty = (visibleSize * 100) / itemSize;
            if (visibilty<0) {
                visibilty = visibilty * -1;
            }
            if (visibilty > 100) {
                visibilty = 100;
            }else if(isAggressive){
                visibilty = visibilty / 2;
            }
            if (visibilty<0) {
                visibilty = visibilty * -1;
            }else {
                visibilty = 100 - visibilty;
            }
            fadeView(i, visibilty);
            if(vListener!=null) {
                vListener.onVisibleChanged(i,visibilty);
            }
        }
    }

    private ViewHolder tempViewHolder;
    private ColorDrawable mColorDrawable;
    private void fadeView(int position, int visibilty) {
        tempViewHolder = findViewHolderForLayoutPosition(position);
        if(tempViewHolder!=null){//and it should not but..
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isFadeModeAlpha) {
                int transperancyColor = Color.argb(visibilty * 255 / 100, red, green, blue);
                mColorDrawable = new ColorDrawable(transperancyColor);
                tempViewHolder.itemView.setForeground(mColorDrawable);
            }else{
                //ugly? & expensive!
                tempViewHolder.itemView.setAlpha((float)(100-visibilty)/100f);
            }
        }
    }

    public interface VisibilityListener {
        void onVisibleChanged(int position, int visibilityPercentage);
    }

    public void setVisibilityListener(VisibilityListener visibilityListener) {
        vListener = visibilityListener;
    }

    public void setFadeModeAlpha(boolean fadeModeAlpha) {
        isFadeModeAlpha = fadeModeAlpha;
    }

}
