package com.neurotech.basecore.ui.adapter.base;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.neurotech.basecore.interfaces.OnDragAndSwipeListener;

/**
 * Created by NeuroAndroid on 2017/8/22.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnDragAndSwipeListener mOnDragAndSwipeListener;

    /**
     * 是否可以拖拽
     */
    private boolean isCanDrag = false;

    /**
     * 是否可以被滑动
     */
    private boolean isCanSwipe = false;

    public SimpleItemTouchHelperCallback(OnDragAndSwipeListener onDragAndSwipeListener) {
        mOnDragAndSwipeListener = onDragAndSwipeListener;
    }

    // 处理拖动的方向以及侧滑的方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // 设置拖拽方向为上下左右
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            // 不支持侧滑
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            // 设置拖拽方向为上下
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 设置侧滑方向为从左到右或者从右到左都可以
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
    }

    public void setCanSwipe(boolean canSwipe) {
        isCanSwipe = canSwipe;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isCanDrag;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwipe;
    }

    // 拖动回调的方法
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        mOnDragAndSwipeListener.onItemDrag(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    // 侧滑回调方法
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mOnDragAndSwipeListener.onItemSwipe(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // 正在拖拽或者侧滑  不处于闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            mOnDragAndSwipeListener.onItemSelected(viewHolder);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        mOnDragAndSwipeListener.onItemClear(viewHolder);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //当前状态为侧滑
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //根据侧滑的距离来修改item的透明度
            mOnDragAndSwipeListener.onItemSwipeAlpha(viewHolder, dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
