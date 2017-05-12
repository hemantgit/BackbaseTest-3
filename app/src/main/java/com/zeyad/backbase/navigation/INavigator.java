package com.zeyad.backbase.navigation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

public interface INavigator {
    void navigateTo(Context context, Intent intent);

    void navigateTo(Context context, Intent intent, ActivityOptions activityOptions);

    void startForResult(Activity activity, Intent intent, int requestCode);
}
