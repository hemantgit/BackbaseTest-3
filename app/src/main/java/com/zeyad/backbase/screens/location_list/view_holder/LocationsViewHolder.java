package com.zeyad.backbase.screens.location_list.view_holder;

import android.view.View;
import android.widget.TextView;

import com.zeyad.backbase.R;
import com.zeyad.backbase.adapter.GenericRecyclerViewAdapter;
import com.zeyad.backbase.screens.location_list.Bookmark;

/**
 * @author by ZIaDo on 5/11/17.
 */
public class LocationsViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    private TextView tvLocationName;

    public LocationsViewHolder(View view) {
        super(view);
        tvLocationName = (TextView) view.findViewById(R.id.tv_location_name);
    }

    @Override
    public void bindData(Object data, boolean isItemSelected, int position, boolean isEnabled) {
        if (data instanceof Bookmark) {
            Bookmark bookmark = (Bookmark) data;
            tvLocationName.setText(bookmark.getName());
        }
    }

    @Override
    public void expand(boolean isExpanded) {

    }
}
