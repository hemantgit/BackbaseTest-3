package com.zeyad.backbase.screens.location_detail.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author by ZIaDo on 5/12/17.
 */

class Rain {

    @SerializedName("3h")
    @Expose
    private Float _3h;

    public Float get3h() {
        return _3h;
    }

    public void set3h(Float _3h) {
        this._3h = _3h;
    }
}
