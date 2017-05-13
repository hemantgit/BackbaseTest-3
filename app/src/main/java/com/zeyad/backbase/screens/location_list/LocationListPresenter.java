package com.zeyad.backbase.screens.location_list;

import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeyad.backbase.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by ZIaDo on 5/11/17.
 */
class LocationListPresenter {

    private Gson gson;

    LocationListPresenter(Gson gson) {
        this.gson = gson;
    }

    List<Bookmark> getBookMarkedLocations(SharedPreferences sharedPreferences) {
        return ((List<Bookmark>) gson.fromJson(sharedPreferences.getString(Constants.BOOKMARKS_KEY, ""),
                new TypeToken<List<Bookmark>>() {
                }.getType()));
    }

    void bookmarkLocation(SharedPreferences sharedPreferences, LatLng latLng, String name) {
        List<Bookmark> bookmarks = getBookMarkedLocations(sharedPreferences);
        if (bookmarks == null)
            bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark(latLng, name));
        sharedPreferences.edit().putString(Constants.BOOKMARKS_KEY, gson.toJson(bookmarks)).apply();
    }

    Bookmark[] getBookMarkedLocationsArray(SharedPreferences sharedPreferences) {
        Bookmark[] bookmarks = new Bookmark[0];
        List<Bookmark> bookMarkedLocations = getBookMarkedLocations(sharedPreferences);
        if (bookMarkedLocations != null)
            bookMarkedLocations.toArray(bookmarks);
        return bookmarks;
    }
}
