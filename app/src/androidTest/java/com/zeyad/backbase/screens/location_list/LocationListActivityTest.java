package com.zeyad.backbase.screens.location_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.zeyad.backbase.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LocationListActivityTest {

    @Rule
    public ActivityTestRule<LocationListActivity> mActivityTestRule = new ActivityTestRule<>(LocationListActivity.class);

    @Test
    public void locationListActivityTest() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withContentDescription("Google Map")).perform(click());
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_locations), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
    }

}
