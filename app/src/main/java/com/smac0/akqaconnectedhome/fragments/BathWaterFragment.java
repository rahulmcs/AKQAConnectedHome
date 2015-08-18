
package com.smac0.akqaconnectedhome.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.inject.Inject;
import com.smac0.akqaconnectedhome.BuildConfig;
import com.smac0.akqaconnectedhome.R;
import com.smac0.akqaconnectedhome.application.Properties;
import com.smac0.akqaconnectedhome.fragments.listeners.BathSensorListener;
import com.smac0.akqaconnectedhome.fragments.presenters.BathWaterFragmentPresenter;
import com.smac0.akqaconnectedhome.fragments.views.BathView;
import com.smac0.akqaconnectedhome.fragments.views.LoadingView;
import com.smac0.akqaconnectedhome.model.BathWaterTemperature;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import java.util.concurrent.TimeUnit;

/**
 * Implements the Bath Water Simulator UI as a standalone re-usable component following the MVP
 * pattern. Also exposes a Sensor Listener to notify of changes in current water level and cold and
 * hot temperature values.
 */
public class BathWaterFragment extends RoboFragment implements BathView,
        View.OnClickListener {
    public static final String TAG = BathWaterFragment.class.getName();

    private static final String OBJECT_ANIMATOR_PROPERTY_WATER_LEVEL = "waterLevel";
    private static final String OBJECT_ANIMATOR_PROPERTY_WATER_LEVEL_Y = "waterLevelY";

    @InjectView(R.id.hot_tap_button)
    Button mHotTapButton;

    @InjectView(R.id.cold_tap_button)
    Button mColdTapButton;

    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @InjectView(R.id.bath_monitor_container)
    ViewGroup mBathMonitorParent;

    @InjectView(R.id.bath_monitor_water)
    ImageView mBathWaterView;

    @Inject
    BathWaterFragmentPresenter mFragmentPresenter;

    private FrameLayout.LayoutParams mBathWaterViewLayoutParams;
    private ObjectAnimator mFillWaterAnim;
    private BathWaterTemperature mBathWaterTemperature;

    private boolean mHotWaterTapOn;
    private boolean mColdWaterTapOn;
    private int mCurrentWaterLevel;

    private BathSensorListener mListener;

    public BathWaterFragment() {
    }

    public static BathWaterFragment newInstance() {
        return new BathWaterFragment();
    }

    public void setListener(BathSensorListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentPresenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bath_water_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
        mFragmentPresenter.setView(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentPresenter.getBathTemperature();
    }

    @Override
    public void onResume() {
        super.onResume();

        mFragmentPresenter.onResume();
        startFillWaterAnim();
    }

    @Override
    public void onPause() {
        super.onPause();

        mFragmentPresenter.onPause();
        stopFillWaterAnim();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mFragmentPresenter.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mColdTapButton.getId()) {
            toggleOnColdTap();
        } else if (view.getId() == mHotTapButton.getId()) {
            toggleOnHotTap();
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWaterTemperatureFetchError(String reason) {
        Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setWaterTemperature(BathWaterTemperature bathWaterTemperature) {
        mBathWaterTemperature = bathWaterTemperature;

        String temperature = getString(R.string.bath_temperature_retrieval_success,mBathWaterTemperature.getHotWaterTemperature(),
                mBathWaterTemperature.getColdWaterTemperature());

        Toast.makeText(getActivity(), temperature, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    public void setWaterLevel(float waterLevel) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, OBJECT_ANIMATOR_PROPERTY_WATER_LEVEL + ": " + waterLevel);

        mCurrentWaterLevel = (int) waterLevel;
        sendWaterLevelChanged();
    }

    public void setWaterLevelY(float waterLevelY) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, OBJECT_ANIMATOR_PROPERTY_WATER_LEVEL_Y + ": " + waterLevelY);

        // update height of mBathWaterView to reflect water level
        if (mBathWaterViewLayoutParams == null) {
            mBathWaterViewLayoutParams = (FrameLayout.LayoutParams) mBathWaterView
                    .getLayoutParams();
        }
        mBathWaterViewLayoutParams.height = (int) waterLevelY;
        mBathWaterView.setLayoutParams(mBathWaterViewLayoutParams);
    }

    /*
     * Animation to simulate the Water filling into the Bath. This is achieved by interpolating on
     * two properties - the current waterlevel from 0 to Properties.BATH_MAX_CAPACITY_LITRES and the
     * actual water level view whose height starts from 0 Bath Container Height. The actual
     * interpolation is done over a period as calculated by the tap(s) currently On and the rate at
     * which water fills in from the tap per min. It is assumed that if both taps are on the rate
     * will be faster.
     */
    private void startFillWaterAnim() {
        stopFillWaterAnim();

        if (mBathWaterTemperature != null && isAnyTapOn()) {
            PropertyValuesHolder pvWaterLevelY = PropertyValuesHolder.ofFloat(
                    OBJECT_ANIMATOR_PROPERTY_WATER_LEVEL_Y, mBathWaterView.getHeight(),
                    getBathHeight());
            PropertyValuesHolder pvWaterLevel = PropertyValuesHolder.ofFloat(
                    OBJECT_ANIMATOR_PROPERTY_WATER_LEVEL,
                    mCurrentWaterLevel, Properties.BATH_MAX_CAPACITY_LITRES);

            mFillWaterAnim = ObjectAnimator.ofPropertyValuesHolder(this, pvWaterLevel,
                    pvWaterLevelY);
            mFillWaterAnim.setDuration(TimeUnit.MINUTES
                    .toMillis((Properties.BATH_MAX_CAPACITY_LITRES - mCurrentWaterLevel)
                            / getFlowPerMin()));

            mFillWaterAnim.start();
        }
    }

    private void stopFillWaterAnim() {
        if (mFillWaterAnim != null) {
            mFillWaterAnim.cancel();
        }
    }

    private int getFlowPerMin() {
        int flowPerMin = 0;
        if (mHotWaterTapOn) {
            flowPerMin += Properties.HOT_TAP_FLOW_PER_MIN;
        }
        if (mColdWaterTapOn) {
            flowPerMin += Properties.COLD_TAP_FLOW_PER_MIN;
        }
        return flowPerMin;
    }

    private boolean isAnyTapOn() {
        return mHotWaterTapOn || mColdWaterTapOn;
    }

    private void setListeners() {
        mColdTapButton.setOnClickListener(this);
        mHotTapButton.setOnClickListener(this);
    }

    private void toggleOnHotTap() {
        mHotWaterTapOn = !mHotWaterTapOn;
        startFillWaterAnim();
        sendHotTemperatureChanged(mHotWaterTapOn);
        toggleRotateButton(mHotTapButton, mHotWaterTapOn);
    }

    private void toggleOnColdTap() {
        mColdWaterTapOn = !mColdWaterTapOn;
        startFillWaterAnim();
        sendColdTemperatureChanged(mColdWaterTapOn);
        toggleRotateButton(mColdTapButton, mColdWaterTapOn);
    }

    private void toggleRotateButton(Button button, boolean on) {
        if (on) {
            button.setRotation(50);
        } else {
            button.setRotation(0);
        }
    }

    private float getBathHeight() {
        // account for bath tank border pixels ~ 15px)
        return (mBathMonitorParent.getHeight() - (15 * getResources()
                .getDisplayMetrics().density));
    }

    private void sendHotTemperatureChanged(boolean hot) {
        if (mListener != null) {
            mListener.onHotWaterTemperatureChanged(hot ? mBathWaterTemperature
                    .getHotWaterTemperature() : 0);
        }
    }

    private void sendColdTemperatureChanged(boolean cold) {
        if (mListener != null) {
            mListener.onColdWaterTemperatureChanged(cold ? mBathWaterTemperature
                    .getColdWaterTemperature() : 0);
        }
    }

    private void sendWaterLevelChanged() {
        if (mListener != null) {
            mListener.onWaterLevelChanged(mCurrentWaterLevel);
        }
    }
}
