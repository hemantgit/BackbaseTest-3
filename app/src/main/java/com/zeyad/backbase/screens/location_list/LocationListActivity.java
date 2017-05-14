package com.zeyad.backbase.screens.location_list;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zeyad.backbase.R;
import com.zeyad.backbase.adapter.GenericRecyclerViewAdapter;
import com.zeyad.backbase.adapter.ItemInfo;
import com.zeyad.backbase.base.BaseActivity;
import com.zeyad.backbase.screens.location_detail.LocationDetailActivity;
import com.zeyad.backbase.screens.location_detail.LocationDetailFragment;
import com.zeyad.backbase.screens.location_list.view_holder.EmptyViewHolder;
import com.zeyad.backbase.screens.location_list.view_holder.LocationsViewHolder;
import com.zeyad.backbase.screens.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.GoogleMap.OnMapClickListener;

/**
 * An activity representing a list of locations. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link LocationDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class LocationListActivity extends BaseActivity<Bookmark[]> implements OnMapReadyCallback,
        OnMarkerClickListener, OnMapClickListener {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private GoogleMap mMap;
    private GenericRecyclerViewAdapter locationsAdapter;
    private RecyclerView rvLocation;
    private LocationListPresenter locationListPresenter;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private List<Marker> markers;

    @Override
    public void initialize() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(R.string.loading);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        markers = new ArrayList<>();
        locationListPresenter = new LocationListPresenter(gson);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_location_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        rvLocation = (RecyclerView) findViewById(R.id.rv_locations);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupRecyclerView();
        if (findViewById(R.id.location_detail_container) != null) {
            mTwoPane = true;
        }
    }

    @Override
    public void loadData() {
        toggleViews(true);
        renderState(locationListPresenter.getBookMarkedLocationsArray(sharedPreferences));
        toggleViews(false);
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
                openLocationDetail(((Bookmark) itemInfo.getData()).getLatLng());
            }
        });
        locationsAdapter.setOnItemLongClickListener(new GenericRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position, ItemInfo itemInfo, GenericRecyclerViewAdapter.ViewHolder holder) {
                locationListPresenter.deleteBookmark(sharedPreferences, ((Bookmark) itemInfo.getData()).getName());
                locationsAdapter.removeItem(position);
                markers.get(position).remove();
                markers.remove(position);
                return false;
            }
        });
        locationsAdapter.setAreItemsClickable(true);
        locationsAdapter.setAllowSelection(true);
        rvLocation.setLayoutManager(new LinearLayoutManager(this));
        rvLocation.setAdapter(locationsAdapter);
    }

    private void openLocationDetail(LatLng latLng) {
        if (mTwoPane) {
            LocationDetailFragment fragment = LocationDetailFragment
                    .newInstance(new LatLng(latLng.latitude, latLng.longitude));
            addFragment(R.id.location_detail_container, fragment, fragment.getTag(), null);
        } else {
            navigator.navigateTo(this, LocationDetailActivity.getCallingIntent(this, latLng));
        }
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
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (markers.isEmpty() && viewState != null) {
            for (Bookmark bookMark : viewState) {
                markers.add(mMap.addMarker(new MarkerOptions()
                        .title(bookMark.getName())
                        .position(bookMark.getLatLng())));
            }
            if (!markers.isEmpty())
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 10));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
        marker.setTag(latLng.toString());
        locationListPresenter.bookmarkLocation(sharedPreferences, latLng, latLng.toString());
        if (locationsAdapter.getItemCount() > 0 && locationsAdapter.getFirstItem().getLayoutId() == R.layout.empty_view)
            locationsAdapter.removeItem(0);
        locationsAdapter.appendItem(new ItemInfo<>(new Bookmark(latLng, latLng.toString()),
                R.layout.location_item_layout));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        openLocationDetail(marker.getPosition());
        return false;
    }

    @Override
    public void renderState(Bookmark[] bookmarks) {
        viewState = bookmarks;
        locationsAdapter.setDataList(new ArrayList<ItemInfo>());
        for (Bookmark bookMark : bookmarks) {
            locationsAdapter.appendItem(new ItemInfo<>(bookMark, R.layout.location_item_layout));
            if (mMap != null) {
                markers.add(mMap.addMarker(new MarkerOptions()
                        .title(bookMark.getName())
                        .position(bookMark.getLatLng())));
            }
        }
        if (mMap != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 5));
        if (locationsAdapter.getItemCount() == 0)
            locationsAdapter.appendItem(new ItemInfo<>(null, R.layout.empty_view));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_prefs:
                navigator.navigateTo(this, new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toggleViews(boolean toggle) {
        if (toggle)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        showErrorSnackBar(message, toolbar, Snackbar.LENGTH_LONG);
    }
}
