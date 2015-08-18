
package com.smac0.akqaconnectedhome.application;

import android.app.Application;

import roboguice.RoboGuice;

public class AutomationSimulatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DependencyInstaller.install(this);
    }
}
