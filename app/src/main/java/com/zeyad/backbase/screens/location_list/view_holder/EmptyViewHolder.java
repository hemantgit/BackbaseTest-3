package com.zeyad.backbase.screens.location_list.view_holder;

import android.view.View;

import com.zeyad.backbase.adapter.GenericRecyclerViewAdapter;

/**
 * @author zeyad on 11/29/16.
 */
public class EmptyViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    public EmptyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object data, boolean isItemSelected, int position, boolean isEnabled) {
    }

    @Override
    public void expand(boolean isExpanded) {

    }
}
