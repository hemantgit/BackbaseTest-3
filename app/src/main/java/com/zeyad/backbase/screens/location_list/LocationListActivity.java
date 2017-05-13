package com.zeyad.backbase.screens.location_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zeyad.backbase.R;
import com.zeyad.backbase.adapter.GenericRecyclerViewAdapter;
import com.zeyad.backbase.adapter.ItemInfo;
import com.zeyad.backbase.base.BaseActivity;
import com.zeyad.backbase.screens.location_detail.LocationDetailActivity;
import com.zeyad.backbase.screens.location_detail.LocationDetailFragment;

import java.util.ArrayList;

/**
 * An activity representing a list of locatoins. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link LocationDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class LocationListActivity extends BaseActivity<BookMark[]> implements OnMapReadyCallback {
    private static final String TAG = "MyTag";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private GoogleMap mMap;
    private GenericRecyclerViewAdapter locationsAdapter;
    private RecyclerView locationRecyclerView;
    private LocationListPresenter locationListPresenter;
    private Toolbar toolbar;

    @Override
    public void initialize() {
        locationListPresenter = new LocationListPresenter();
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_location_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupRecyclerView();
        if (findViewById(R.id.locatoin_detail_container) != null) {
            mTwoPane = true;
        }
    }

    @Override
    public void loadData() {
        locationListPresenter.getBookMarkedLocations();
    }

    private void setupRecyclerView() {
        locationsAdapter = new GenericRecyclerViewAdapter((LayoutInflater) getSystemService(Context
                .LAYOUT_INFLATER_SERVICE), new ArrayList<ItemInfo>()) {
            @Override
            public GenericRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case R.layout.empty_view:
                        return new EmptyViewHolder(mLayoutInflater.inflate(R.layout.empty_view, parent,
                                false));
                    case R.layout.location_item_layout:
                        return new LocationsViewHolder(mLayoutInflater.inflate(R.layout.location_item_layout,
                                parent, false));
                    default:
                        return null;
                }
            }
        };
        locationsAdapter.setOnItemClickListener(new GenericRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, ItemInfo itemInfo, GenericRecyclerViewAdapter.ViewHolder holder) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putDouble(LocationDetailFragment.LAT, 0);
                    arguments.putDouble(LocationDetailFragment.LNG, 0);
                    arguments.putBoolean(LocationDetailFragment.TWO_PANE, mTwoPane);
                    LocationDetailFragment fragment = new LocationDetailFragment();
                    fragment.setArguments(arguments);
                    addFragment(R.id.locatoin_detail_container, fragment, fragment.getTag(), null);
                } else {
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, LocationDetailActivity.class);
                    intent.putExtra(LocationDetailFragment.LAT, 0);
                    intent.putExtra(LocationDetailFragment.LNG, 0);
                    intent.putExtra(LocationDetailFragment.TWO_PANE, mTwoPane);
                    context.startActivity(intent);
                }
            }
        });
        locationsAdapter.setAreItemsClickable(true);
        locationsAdapter.setAllowSelection(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(locationsAdapter);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void renderState(BookMark[] bookMarks) {

    }

    @Override
    public void toggleViews(boolean toggle) {

    }

    @Override
    public void showError(String message) {

    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
