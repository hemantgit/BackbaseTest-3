package com.zeyad.backbase.screens.location_detail;

import android.support.test.rule.BuildConfig;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author by ZIaDo on 5/14/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LocationDetailPresenterTest {
    @Test
    public void getWeather() throws Exception {
        RequestQueue requestQueue = mock(RequestQueue.class);
        LocationDetailPresenter locationDetailPresenter = new LocationDetailPresenter(requestQueue);
        locationDetailPresenter.getWeather(0, 0, "metric", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, "TAG");

        verify(requestQueue, times(1)).add(any(Request.class));
    }
}