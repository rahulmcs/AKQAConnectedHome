
package com.smac0.akqaconnectedhome.fragments.views;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.smac0.akqaconnectedhome.model.BathWaterTemperature;

/**
 * Interface representing a view that will show the representation of a Water Bath
 */
public interface BathView extends LoadingView{
    void showWaterTemperatureFetchError(String reason);

    void setWaterTemperature(BathWaterTemperature bathWaterTemperature);

    Context getContext();

    Fragment getFragment();
}
