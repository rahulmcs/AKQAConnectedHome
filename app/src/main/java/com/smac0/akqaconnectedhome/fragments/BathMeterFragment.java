
package com.smac0.akqaconnectedhome.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smac0.akqaconnectedhome.R;
import com.smac0.akqaconnectedhome.application.Properties;
import com.smac0.akqaconnectedhome.fragments.listeners.BathSensorListener;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * Implements the Bath Meter UI as a standalone re-usable component.
 */
public class BathMeterFragment extends RoboFragment implements BathSensorListener {

    public static final String TAG = BathMeterFragment.class.getName();

    @InjectView(R.id.dial_hand)
    ImageView mDialHandView;

    @InjectView(R.id.bath_capacity_meter_digit0)
    TextView mBathCapacityDigit0View;

    @InjectView(R.id.bath_capacity_meter_digit1)
    TextView mBathCapacityDigit1View;

    @InjectView(R.id.bath_capacity_meter_digit2)
    TextView mBathCapacityDigit2View;

    private int mHotWaterTemperature;
    private int mColdWaterTemperature;

    public BathMeterFragment() {
    }

    public static BathMeterFragment newInstance() {
        return new BathMeterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bath_meter, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateDialTemperature();
        onWaterLevelChanged(0);
    }

    private void updateDialTemperature() {
        int temperature = 0;

        if (mHotWaterTemperature > 0) {
            temperature += mHotWaterTemperature;
        }

        if (mColdWaterTemperature > 0) {
            temperature -= mColdWaterTemperature;
        }

        //set the rotation angle of the dial hand to indicate the temperature
        //Note the temperature is a mixture of hot and cold water
        //Note -90 indicates cold water at 0 degrees and the max temperature is
        // 60 degrees
        mDialHandView.setRotation(-90 + (Math.abs(temperature) * 3));
    }

    @Override
    public void onColdWaterTemperatureChanged(int temperature) {
        mColdWaterTemperature = temperature;
        updateDialTemperature();
    }

    @Override
    public void onHotWaterTemperatureChanged(int temperature) {
        mHotWaterTemperature = temperature;
        updateDialTemperature();
    }

    @Override
    public void onWaterLevelChanged(int waterLevel) {
        if (waterLevel <= Properties.BATH_MAX_CAPACITY_LITRES) {
            mBathCapacityDigit2View.setText(String.valueOf(waterLevel % 10));
            waterLevel /= 10;
            mBathCapacityDigit1View.setText(String.valueOf(waterLevel % 10));
            waterLevel /= 10;
            mBathCapacityDigit0View.setText(String.valueOf(waterLevel % 10));
        }
    }
}
