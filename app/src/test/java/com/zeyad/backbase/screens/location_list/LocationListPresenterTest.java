package com.zeyad.backbase.screens.location_list;

import android.content.SharedPreferences;
import android.support.test.rule.BuildConfig;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.zeyad.backbase.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author by ZIaDo on 5/14/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LocationListPresenterTest {
    LocationListPresenter locationListPresenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Before
    public void setUp() throws Exception {
        sharedPreferences = mock(SharedPreferences.class);
        locationListPresenter = new LocationListPresenter(new Gson());
        editor = mock(SharedPreferences.Editor.class);
        when(sharedPreferences.getString(Constants.BOOKMARKS_KEY, "")).thenReturn("[]");
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        doNothing().when(editor).apply();
    }

    @Test
    public void bookmarkLocation() throws Exception {
        locationListPresenter.bookmarkLocation(sharedPreferences, new LatLng(0, 0), "");
        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1)).putString(anyString(), anyString());
        verify(editor, times(1)).apply();
    }

    @Test
    public void getBookMarkedLocationsArray() throws Exception {
        locationListPresenter.getBookMarkedLocationsArray(sharedPreferences);
        verify(sharedPreferences, times(1)).getString(anyString(), anyString());
    }

    @Test
    public void deleteBookmark() throws Exception {
        locationListPresenter.deleteBookmark(sharedPreferences, "");
        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1)).putString(anyString(), anyString());
        verify(editor, times(1)).apply();
    }

}