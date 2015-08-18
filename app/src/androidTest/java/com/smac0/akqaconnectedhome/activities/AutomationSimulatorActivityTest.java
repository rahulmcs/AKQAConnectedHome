
package com.smac0.akqaconnectedhome.activities;

import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

import com.smac0.akqaconnectedhome.R;
import com.smac0.akqaconnectedhome.fragments.AutomationSimulatorFragment;
import com.smac0.akqaconnectedhome.fragments.BathMeterFragment;
import com.smac0.akqaconnectedhome.fragments.BathWaterFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.not;

/**
 * Created by rahulchaudhari on 16/08/15.
 */
public class AutomationSimulatorActivityTest extends
        ActivityInstrumentationTestCase2<AutomationSimulatorActivity> {
    private AutomationSimulatorActivity automationSimulatorActivity;

    public AutomationSimulatorActivityTest() {
        super(AutomationSimulatorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        automationSimulatorActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsAutomationSimulatorFragment() {
        Fragment fragment =
                automationSimulatorActivity.getSupportFragmentManager().findFragmentByTag(
                        AutomationSimulatorFragment.TAG);
        assertThat(fragment, is(notNullValue()));
    }

    public void testContainsBathMeterFragment() {
        Fragment fragment =
                automationSimulatorActivity.getSupportFragmentManager().findFragmentByTag(
                        AutomationSimulatorFragment.TAG);
        assertThat(fragment, is(notNullValue()));
        Fragment childFragment = fragment.getChildFragmentManager().findFragmentByTag(
                BathMeterFragment.TAG);
        assertThat(childFragment, is(notNullValue()));
    }

    public void testContainsBathWaterFragment() {
        Fragment fragment =
                automationSimulatorActivity.getSupportFragmentManager().findFragmentByTag(
                        AutomationSimulatorFragment.TAG);
        assertThat(fragment, is(notNullValue()));
        Fragment childFragment = fragment.getChildFragmentManager().findFragmentByTag(
                BathWaterFragment.TAG);
        assertThat(childFragment, is(notNullValue()));
    }

    public void testFragmentAutomationSimulatorAllViewsDisplayed() {
        onView(withId(R.id.bath_fragment_container))
                .check(matches(isDisplayed()));
    }

    public void testFragmentBathMeterAllViewsDisplayed() {
        onView(withId(R.id.temperature_dial))
                .check(matches(isDisplayed()));

        onView(withId(R.id.dial_hand))
                .check(matches(isDisplayed()));

        onView(withId(R.id.bath_capacity_meter))
                .check(matches(isDisplayed()));

        onView(withId(R.id.bath_capacity_meter_digit0))
                .check(matches(isDisplayed()));

        onView(withId(R.id.bath_capacity_meter_digit1))
                .check(matches(isDisplayed()));

        onView(withId(R.id.bath_capacity_meter_digit2))
                .check(matches(isDisplayed()));

    }

    public void testFragmentBathWaterAllViewsDisplayed() {
        onView(withId(R.id.bath_monitor_container))
                .check(matches(isDisplayed()));

        onView(withId(R.id.bath_monitor))
                .check(matches(isDisplayed()));

        onView(withId(R.id.bath_monitor_water))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.hot_tap_button))
                .check(matches(isDisplayed()));

        onView(withId(R.id.cold_tap_button))
                .check(matches(isDisplayed()));

        onView(withId(R.id.hot_button_label))
                .check(matches(isDisplayed()));

        onView(withId(R.id.cold_button_label))
                .check(matches(isDisplayed()));

    }
    // TODO Add more tests to test the tap on and off scenarios and CountingIdlingResource
    // to simulate a load of temperature from mock network to complete entire flow

}
