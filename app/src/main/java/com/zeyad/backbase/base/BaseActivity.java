package com.zeyad.backbase.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zeyad.backbase.navigation.INavigator;
import com.zeyad.backbase.navigation.NavigatorFactory;
import com.zeyad.backbase.snackbar.SnackBarFactory;

import java.io.Serializable;
import java.util.List;


/**
 * @author zeyad on 11/28/16.
 */
public abstract class BaseActivity<S extends Serializable> extends AppCompatActivity implements LoadDataView<S> {
    public static final String UI_MODEL = "viewState";
    public INavigator navigator;
    public S viewState;
    public Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = NavigatorFactory.getInstance();
        gson = new Gson();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (savedInstanceState != null)
            viewState = (S) savedInstanceState.getSerializable(UI_MODEL);
        initialize();
        setupUI();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            viewState = (S) savedInstanceState.getSerializable(UI_MODEL);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null)
            outState.putAll(saveState());
        super.onSaveInstanceState(outState);
    }

    /**
     * To implement! Saves the viewState of the current view. Do not return null!
     *
     * @return {@link Bundle}
     */
    private Bundle saveState() {
        Bundle bundle = new Bundle(1);
        bundle.putSerializable(UI_MODEL, viewState);
        return bundle;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    /**
     * Initialize any objects or any required dependencies.
     */
    public abstract void initialize();

    /**
     * Setup the UI.
     */
    public abstract void setupUI();

    /**
     * Load main view data
     */
    public abstract void loadData();

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    public void addFragment(int containerViewId, Fragment fragment, String currentFragTag,
                            List<Pair<View, String>> sharedElements) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (sharedElements != null)
            for (Pair<View, String> pair : sharedElements)
                fragmentTransaction.addSharedElement(pair.first, pair.second);
        if (currentFragTag == null || currentFragTag.isEmpty())
            fragmentTransaction.addToBackStack(fragment.getTag());
        else fragmentTransaction.addToBackStack(currentFragTag);
        fragmentTransaction.add(containerViewId, fragment, fragment.getTag()).commit();
    }

    public void removeFragment(String tag) {
        getSupportFragmentManager().beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag(tag))
                .commit();
    }

    public void showToastMessage(String message) {
        showToastMessage(message, Toast.LENGTH_LONG);
    }

    public void showToastMessage(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    /**
     * Shows a {@link android.support.design.widget.Snackbar} message.
     *
     * @param message An string representing a message to be shown.
     */
    public void showSnackBarMessage(View view, String message, int duration) {
        if (view != null)
            SnackBarFactory.getSnackBar(SnackBarFactory.TYPE_INFO, view, message, duration).show();
        else throw new NullPointerException("view is null");
    }

    public void showSnackBarWithAction(@SnackBarFactory.SnackBarType String typeSnackBar, View view,
                                       String message, String actionText, View.OnClickListener onClickListener) {
        if (view != null)
            SnackBarFactory.getSnackBarWithAction(typeSnackBar, view, message, actionText, onClickListener).show();
        else throw new NullPointerException("view is null");
    }

    public void showSnackBarWithAction(@SnackBarFactory.SnackBarType String typeSnackBar, View view, String message,
                                       int actionText, View.OnClickListener onClickListener) {
        showSnackBarWithAction(typeSnackBar, view, message, getString(actionText), onClickListener);
    }

    /**
     * Shows a {@link android.support.design.widget.Snackbar} errorResult message.
     *
     * @param message  An string representing a message to be shown.
     * @param duration Visibility duration.
     */
    public void showErrorSnackBar(String message, View view, int duration) {
        if (view != null)
            SnackBarFactory.getSnackBar(SnackBarFactory.TYPE_ERROR, view, message, duration).show();
        else throw new NullPointerException("view is null");
    }
}
