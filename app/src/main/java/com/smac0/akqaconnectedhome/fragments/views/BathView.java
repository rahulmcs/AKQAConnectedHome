
package com.smac0.akqaconnectedhome.fragments.views;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.smac0.akqaconnectedhome.model.BathWaterTemperature;

/**
 * Created by rahulchaudhari on 15/08/15.
 */
public interface BathView extends LoadingView{
    void showWaterTemperatureFetchError(String reason);

    void setWaterTemperature(BathWaterTemperature bathWaterTemperature);

    Context getContext();

    Fragment getFragment();
}
