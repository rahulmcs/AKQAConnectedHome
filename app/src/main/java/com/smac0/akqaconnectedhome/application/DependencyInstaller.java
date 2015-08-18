package com.smac0.akqaconnectedhome.application;

import android.app.Application;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.smac0.akqaconnectedhome.restservices.BathService;
import com.smac0.akqaconnectedhome.restservices.BathServiceProvider;

import roboguice.RoboGuice;

/**
 * RoboGuice dependency injector module*
 */
public class DependencyInstaller implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(BathService.class).toProvider(BathServiceProvider.class);
    }

    public static void install(Application application) {
        RoboGuice.getOrCreateBaseApplicationInjector(application, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(application), new DependencyInstaller());

    }
}
