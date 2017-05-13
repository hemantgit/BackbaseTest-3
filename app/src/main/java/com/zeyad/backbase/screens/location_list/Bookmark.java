package com.zeyad.backbase.screens.location_list;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * @author by ZIaDo on 5/13/17.
 */
class Bookmark implements Serializable {
    private final LatLng latLng;
    private final String name;

    Bookmark(LatLng latLng, String name) {
        this.latLng = latLng;
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
