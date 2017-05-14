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

    private List<Bookmark> getBookMarkedLocations(SharedPreferences sharedPreferences) {
        return ((List<Bookmark>) gson.fromJson(sharedPreferences.getString(Constants.BOOKMARKS_KEY, ""),
                new TypeToken<List<Bookmark>>() {
                }.getType()));
    }

    void bookmarkLocation(SharedPreferences sharedPreferences, LatLng latLng, String name) {
        List<Bookmark> bookmarks = getBookMarkedLocations(sharedPreferences);
        if (bookmarks == null)
            bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark(latLng, name));
        applyBookmarksToPrefs(sharedPreferences, bookmarks);
    }

    Bookmark[] getBookMarkedLocationsArray(SharedPreferences sharedPreferences) {
        List<Bookmark> bookMarkedLocations = getBookMarkedLocations(sharedPreferences);
        Bookmark[] bookmarks = {};
        if (bookMarkedLocations != null) {
            bookmarks = new Bookmark[bookMarkedLocations.size()];
            bookMarkedLocations.toArray(bookmarks);
        }
        return bookmarks;
    }

    void deleteBookmark(SharedPreferences sharedPreferences, String name) {
        List<Bookmark> bookmarks = getBookMarkedLocations(sharedPreferences);
        for (int i = 0, bookmarksSize = bookmarks.size(); i < bookmarksSize; i++) {
            Bookmark bookmark = bookmarks.get(i);
            if (bookmark.getName().equals(name))
                bookmarks.remove(bookmark);
        }
        applyBookmarksToPrefs(sharedPreferences, bookmarks);
    }

    private void applyBookmarksToPrefs(SharedPreferences sharedPreferences, List<Bookmark> bookmarks) {
        sharedPreferences.edit().putString(Constants.BOOKMARKS_KEY, gson.toJson(bookmarks)).apply();
    }
}
