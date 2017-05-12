package com.zeyad.backbase.screens.location_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeyad.backbase.R;
import com.zeyad.backbase.adapter.GenericRecyclerViewAdapter;

/**
 * @author by ZIaDo on 5/11/17.
 */

public class LocationsViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    TextView tvLocationName;
    ImageView ivDelete;

    public LocationsViewHolder(View view) {
        super(view);
        tvLocationName = (TextView) view.findViewById(R.id.tv_location_name);
        ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
    }

    @Override
    public void bindData(Object data, boolean isItemSelected, int position, boolean isEnabled) {
        if (data instanceof String) {
            tvLocationName.setText(String.valueOf(data));
        }
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void expand(boolean isExpanded) {

    }
}