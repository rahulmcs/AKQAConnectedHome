
package com.smac0.akqaconnectedhome.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.smac0.akqaconnectedhome.R;
import com.smac0.akqaconnectedhome.fragments.AutomationSimulatorFragment;

import roboguice.activity.RoboActionBarActivity;

/**
 * Activity displaying the Home Automation Simulator using delegated child fragments.
 */
public class AutomationSimulatorActivity extends RoboActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, AutomationSimulatorFragment.newInstance(),
                            AutomationSimulatorFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_automation_simuator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        //
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
