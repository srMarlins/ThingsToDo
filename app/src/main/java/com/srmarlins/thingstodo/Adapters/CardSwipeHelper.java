package com.srmarlins.thingstodo.Adapters;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by jfowler on 9/11/15.
 */
public class CardSwipeHelper extends ItemTouchHelper.Callback {

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
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        if(i == ItemTouchHelper.START){

        }else if(i == ItemTouchHelper.END){

        }
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), i);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        ((EventRecyclerViewAdapter.ViewHolder)viewHolder).onSwiped(dX);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return .8f;
    }

    public interface CardSwipeHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position, int direction);
    }

    public interface CardSwipeViewHolderAdapter{
        void onSwiped(double directionX);
    }

}
