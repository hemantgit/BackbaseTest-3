package com.zeyad.backbase.screens.location_detail;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import static com.zeyad.backbase.utils.Constants.URLS.API_BASE_URL;
import static com.zeyad.backbase.utils.Constants.URLS.API_KEY;
import static com.zeyad.backbase.utils.Constants.URLS.TODAY;

/**
 * @author by ZIaDo on 5/11/17.
 */
class LocationDetailPresenter {

    void getWeather(double lat, double lng, String units, RequestQueue queue,
                    Response.Listener<String> response, Response.ErrorListener error, String tag) {
        String url = API_BASE_URL + String.format(TODAY, lat, lng, API_KEY, units);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response, error);
        stringRequest.setTag(tag);
        queue.add(stringRequest);
    }
}
