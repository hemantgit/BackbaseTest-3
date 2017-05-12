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

public class LocationDetailPresenter {

    void getForecast(RequestQueue queue, Response.Listener<String> response, Response.ErrorListener error,
                     String tag) {
        String url = API_BASE_URL + String.format(TODAY, 0, 0, API_KEY, "metric");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response, error);
        stringRequest.setTag(tag);
        queue.add(stringRequest);
    }
}
