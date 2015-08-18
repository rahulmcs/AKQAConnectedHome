package com.smac0.akqaconnectedhome.fragments.presenters;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 * Defines lifecycle methods that should typically be invoked by the View(typically a Fragment)
 * that owns this presenter.
 */
public interface Presenter {

    void onCreate();

    void onResume();

    void onPause();

    void onDestroy();

}
