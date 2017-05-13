package com.zeyad.backbase.screens.location_detail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.zeyad.backbase.R;
import com.zeyad.backbase.base.BaseFragment;
import com.zeyad.backbase.screens.location_detail.models.Forecast;
import com.zeyad.backbase.screens.location_list.LocationListActivity;
import com.zeyad.backbase.utils.Utils;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a single location detail screen.
 * This fragment is either contained in a {@link LocationListActivity}
 * in two-pane mode (on tablets) or a {@link LocationDetailActivity}
 * on handsets.
 */
public class LocationDetailFragment extends BaseFragment<Forecast> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String LAT = "lat", LNG = "lng", TWO_PANE = "twoPane";
    private LocationDetailPresenter locationDetailPresenter;
    private RequestQueue requestQueue;
    private LatLng latLng;
    private boolean isTwoPane;
    private TextView mFriendlyDate;
    private TextView mDate;
    private TextView mDescription;
    private TextView mHighTemp;
    private TextView mLowTemp;
    private TextView mHumidity;
    private TextView mWind;
    private TextView mPressure;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationDetailFragment() {
    }

    public static LocationDetailFragment newInstance(LatLng latLng, boolean isTwoPane) {
        LocationDetailFragment locationDetailFragment = new LocationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(LAT, latLng.latitude);
        bundle.putDouble(LNG, latLng.longitude);
        bundle.putBoolean(TWO_PANE, isTwoPane);
        locationDetailFragment.setArguments(bundle);
        return locationDetailFragment;
    }

    @Override
    public void initialize() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            isTwoPane = arguments.getBoolean(TWO_PANE);
            latLng = new LatLng(arguments.getDouble(LAT, 0), arguments.getDouble(LNG, 0));
        }
        locationDetailPresenter = new LocationDetailPresenter();
    }

    @Override
    public void loadData() {
        requestQueue = Volley.newRequestQueue(getContext());
        toggleViews(true);
        locationDetailPresenter.getForecast(latLng.latitude, latLng.longitude, requestQueue, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                toggleViews(false);
                renderState(gson.fromJson(response, Forecast.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toggleViews(false);
                showError(error.getLocalizedMessage());
            }
        }, TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_detail, container, false);
        mDate = (TextView) rootView.findViewById(R.id.tv_detail_date);
        mFriendlyDate = (TextView) rootView.findViewById(R.id.tv_detail_day);
        mDescription = (TextView) rootView.findViewById(R.id.tv_detail_forecast);
        mHighTemp = (TextView) rootView.findViewById(R.id.tv_detail_high);
        mLowTemp = (TextView) rootView.findViewById(R.id.tv_detail_low);
        mHumidity = (TextView) rootView.findViewById(R.id.tv_detail_humidity);
        mWind = (TextView) rootView.findViewById(R.id.tv_detail_wind);
        mPressure = (TextView) rootView.findViewById(R.id.tv_detail_pressure);
        return rootView;
    }

    @Override
    public void renderState(Forecast forecast) {
        if (isTwoPane) {
            LocationListActivity locationListActivity = (LocationListActivity) getActivity();
            locationListActivity.getToolbar().setTitle(forecast.getName());
        } else {
            LocationDetailActivity locationDetailActivity = (LocationDetailActivity) getActivity();
            locationDetailActivity.getToolbar().setTitle(forecast.getName());
        }
        long date = forecast.getDt();
        mFriendlyDate.setText(Utils.getDayName(getContext(), date));
        mDate.setText(Utils.getFormattedMonthDay(date));
        mDescription.setText(forecast.getWeather().get(0).getDescription());
        mHighTemp.setText(Utils.formatTemperature(getContext(), forecast.getMain().getTempMax()));
        mLowTemp.setText(Utils.formatTemperature(getContext(), forecast.getMain().getTempMin()));
        mHumidity.setText(getContext().getString(R.string.format_humidity, forecast.getMain().getHumidity()));
        mWind.setText(Utils.getFormattedWind(getContext(), forecast.getWind().getSpeed(), forecast.getWind().getDeg()));
        mPressure.setText(getContext().getString(R.string.format_pressure, forecast.getMain().getPressure()));
    }

    @Override
    public void toggleViews(boolean toggle) {

    }

    @Override
    public void showError(String message) {
        showErrorSnackBar(message, mFriendlyDate, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onStop() {
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
        super.onStop();
    }
}
