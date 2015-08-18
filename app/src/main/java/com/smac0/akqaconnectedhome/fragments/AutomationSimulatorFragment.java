
package com.smac0.akqaconnectedhome.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smac0.akqaconnectedhome.R;

import roboguice.fragment.RoboFragment;

/**
 * Parent fragment composed of individual fragments that together
 * form the Home Automation Simulator UI.
 */
public class AutomationSimulatorFragment extends RoboFragment {

    public static final String TAG = AutomationSimulatorFragment.class.getName();

    private BathMeterFragment mBathMeterFragment;
    private BathWaterFragment mBathWaterFragment;

    public AutomationSimulatorFragment() {
    }

    public static AutomationSimulatorFragment newInstance() {
        return new AutomationSimulatorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_automation_simulator, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            addChildFragments();
        }
    }

    private void addChildFragments() {
        // add Bath meter and Bath Water Fragments
        mBathMeterFragment = BathMeterFragment.newInstance();
        mBathWaterFragment = BathWaterFragment.newInstance();
        mBathWaterFragment.setListener(mBathMeterFragment);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction
                .add(R.id.bath_fragment_container, mBathMeterFragment, BathMeterFragment.TAG)
                .add(R.id.bath_fragment_container, mBathWaterFragment, BathWaterFragment.TAG)
                .commit();

    }
}
