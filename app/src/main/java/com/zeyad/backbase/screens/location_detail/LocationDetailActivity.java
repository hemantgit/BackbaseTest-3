package com.zeyad.backbase.screens.location_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;
import com.zeyad.backbase.R;
import com.zeyad.backbase.screens.location_list.LocationListActivity;

/**
 * An activity representing a single location detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link LocationListActivity}.
 */
public class LocationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            LocationDetailFragment fragment = LocationDetailFragment.newInstance(new LatLng(
                    getIntent().getDoubleExtra(LocationDetailFragment.LNG, 0),
                    getIntent().getDoubleExtra(LocationDetailFragment.LAT, 0)));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.location_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getCallingIntent(Context context, LatLng latLng) {
        Intent intent = new Intent(context, LocationDetailActivity.class);
        intent.putExtra(LocationDetailFragment.LAT, latLng.latitude);
        intent.putExtra(LocationDetailFragment.LNG, latLng.longitude);
        return intent;
    }
}
