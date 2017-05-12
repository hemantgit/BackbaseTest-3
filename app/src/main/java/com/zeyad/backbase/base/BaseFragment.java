package com.zeyad.backbase.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zeyad.backbase.navigation.INavigator;
import com.zeyad.backbase.navigation.NavigatorFactory;
import com.zeyad.backbase.snackbar.SnackBarFactory;

import java.io.Serializable;

import static com.zeyad.backbase.base.BaseActivity.UI_MODEL;

/**
 * @author zeyad on 11/28/16.
 */
public abstract class BaseFragment<S extends Serializable> extends Fragment implements LoadDataView<S> {

    public INavigator navigator;
    public Gson gson;
    public S viewState;

    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        navigator = NavigatorFactory.getInstance();
        gson = new Gson();
        if (savedInstanceState != null)
            viewState = (S) savedInstanceState.getSerializable(UI_MODEL);
        initialize();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
    public void onStart() {
        super.onStart();
        loadData();
    }

    /**
     * Initialize any objects or any required dependencies.
     */
    public abstract void initialize();

    /**
     * Load main view data
     */
    public abstract void loadData();

    public void showToastMessage(String message) {
        showToastMessage(message, Toast.LENGTH_LONG);
    }

    public void showToastMessage(String message, int duration) {
        Toast.makeText(getContext(), message, duration).show();
    }

    /**
     * Shows a {@link android.support.design.widget.Snackbar} message.
     *
     * @param message An string representing a message to be shown.
     */
    public void showSnackBarMessage(View view, String message, int duration) {
        if (view != null)
            SnackBarFactory.getSnackBar(SnackBarFactory.TYPE_INFO, view, message, duration).show();
        else throw new NullPointerException("View is null");
    }

    public void showSnackBarWithAction(@SnackBarFactory.SnackBarType String typeSnackBar, View view,
                                       String message, String actionText, View.OnClickListener onClickListener) {
        if (view != null)
            SnackBarFactory.getSnackBarWithAction(typeSnackBar, view, message, actionText,
                    onClickListener).show();
        else throw new NullPointerException("View is null");
    }

    public void showSnackBarWithAction(@SnackBarFactory.SnackBarType String typeSnackBar, View view,
                                       String message, int actionText, View.OnClickListener onClickListener) {
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
        else throw new NullPointerException("View is null");
    }
}
