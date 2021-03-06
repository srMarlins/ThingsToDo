package com.srmarlins.thingstodo.Adapters;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by jfowler on 9/11/15.
 */
public class CardSwipeHelper extends ItemTouchHelper.Callback {

    public static final float SWIPE_THRESHOLD = .85f;

    private final CardSwipeHelperAdapter mAdapter;

    public CardSwipeHelper(CardSwipeHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, swipeFlags) |
                makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), i);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        ((EventRecyclerViewAdapter.ViewHolder) viewHolder).onSwiped(dX);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return SWIPE_THRESHOLD;
    }

    public interface CardSwipeHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position, int direction);
    }

    public interface CardSwipeViewHolderAdapter {
        void onSwiped(double directionX);
    }

}
